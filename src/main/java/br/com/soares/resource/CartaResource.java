package br.com.soares.resource;

import br.com.soares.entity.Carta;
import br.com.soares.entity.Classe;
import br.com.soares.entity.Tipo;
import br.com.soares.repository.CartaRepository;
import io.quarkus.panache.common.Parameters;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/carta")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartaResource {

    private static final Logger LOGGER = Logger.getLogger(CartaResource.class.getName());
    @Inject
    CartaRepository cartaRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Carta> listCartas() {
        return cartaRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Carta getOneById(@PathParam("id") Long id) {
        Carta carta = cartaRepository.findById(id);
        if (carta == null) {
            throw new WebApplicationException("A carta de id: " + id + "não existe", 404);
        }
        return carta;
    }

    @GET
    @Path("find")
    public List<Carta> find(@QueryParam("nome") String nome,
                            @QueryParam("id") Long id,
                            @QueryParam("classe") Classe classe,
                            @QueryParam("tipo") Tipo tipo) {
        return cartaRepository.findBySearch(id, nome, classe, tipo);
    }

    @POST
    @Transactional
    public Response create(Carta carta) {
        if (carta.id != null) {
            throw new WebApplicationException("O id não pode ser enviado na requisição.", 422);
        }

        cartaRepository.persist(carta);
        return Response.ok(carta).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Carta update(@PathParam("id") Long id, @Valid Carta carta) {
        if (carta.nome == null) {
            throw new WebApplicationException("O nome da carta não foi enviado na requisição.", 422);
        }

        Carta cartaToEdit = cartaRepository.findById(id);

        if (cartaToEdit == null) {
            throw new WebApplicationException("Carta com o id: " + id + " não existe.", 404);
        }

        cartaToEdit.nome = carta.nome;
        cartaToEdit.descricao = carta.descricao;
        cartaToEdit.defesa = carta.defesa;
        cartaToEdit.ataque = carta.ataque;
        cartaToEdit.classe = carta.classe;
        cartaToEdit.tipo = carta.tipo;

        return cartaToEdit;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Carta carta = cartaRepository.findById(id);
        if (carta == null) {
            throw new WebApplicationException("Carta com o id: " + id + " não existe.", 404);
        }
        cartaRepository.delete(carta);
        return Response.status(204).build();
    }
}