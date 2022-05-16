package br.com.soares.resource;

import br.com.soares.entity.Baralho;
import br.com.soares.repository.BaralhoRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("{id}")
    public Baralho getOneById(@PathParam("id") Long id) {
        Baralho baralho = baralhoRepository.findById(id);
        if (baralho == null) {
            throw new WebApplicationException("A baralho de id: " + id + "não existe", 404);
        }
        return baralho;
    }

    @POST
    @Transactional
    public Response create(Baralho baralho) {
        if (baralho.id != null) {
            throw new WebApplicationException("O id não pode ser enviado na requisição.", 422);
        }

        baralhoRepository.persist(baralho);
        return Response.ok(baralho).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Baralho update(@PathParam("id") Long id, @Valid Baralho baralho) {
        if (baralho.nome == null) {
            throw new WebApplicationException("O nome da baralho não foi enviado na requisição.", 422);
        }

        Baralho baralhoToEdit = baralhoRepository.findById(id);

        if (baralhoToEdit == null) {
            throw new WebApplicationException("Baralho com o id: " + id + " não existe.", 404);
        }

        baralhoToEdit.nome = baralho.nome;
        baralhoToEdit.cartas = baralho.cartas;

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