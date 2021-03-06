package br.com.soares.resource;

import br.com.soares.entity.Baralho;
import br.com.soares.entity.Classe;
import br.com.soares.repository.BaralhoRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/baralho")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BaralhoResource {

    private static final Logger LOGGER = Logger.getLogger(BaralhoResource.class.getName());
    @Inject
    BaralhoRepository baralhoRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Baralho> listBaralhos() {
        return baralhoRepository.listAll();
    }

    @GET
    @Path("find")
    public List<Baralho> find(@QueryParam("nome") String nome,
                              @QueryParam("id") Long id,
                              @QueryParam("classe") Classe classe
    ) {
        return baralhoRepository.findBySearch(id, nome, classe);
    }

    @GET
    @Path("{id}")
    public Baralho getOneById(@PathParam("id") Long id) {
        Baralho baralho = baralhoRepository.findById(id);
        if (baralho == null) {
            throw new WebApplicationException("A baralho de id: " + id + "não existe", Status.NOT_FOUND);
        }
        return baralho;
    }

    @POST
    @Transactional
    public Response create(Baralho baralho) {
        if (baralho.getId() != null) {
            throw new WebApplicationException("O id não pode ser enviado na requisição.", 422);
        }
        if ((long) baralho.getCartas().size() > 30) {
            throw new WebApplicationException("O baralho não pode ter mais de 30 cartas.", Status.EXPECTATION_FAILED);
        }
        baralhoRepository.persist(baralho);
        return Response.ok(baralho).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Baralho update(@PathParam("id") Long id, @Valid Baralho baralho) {
        if (baralho.getNome() == null) {
            throw new WebApplicationException("O nome da baralho não foi enviado na requisição.", 422);
        }

        Baralho baralhoToEdit = baralhoRepository.findById(id);

        if (baralhoToEdit == null) {
            throw new WebApplicationException("Baralho com o id: " + id + " não existe.", 404);
        }

        baralhoToEdit.setNome(baralho.getNome());
        baralhoToEdit.setCartas(baralho.getCartas());

        baralhoRepository.persist(baralhoToEdit);

        return baralhoToEdit;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Baralho baralho = baralhoRepository.findById(id);
        if (baralho == null) {
            throw new WebApplicationException("Baralho com o id: " + id + " não existe.", 404);
        }
        baralhoRepository.delete(baralho);
        return Response.status(204).build();
    }
}