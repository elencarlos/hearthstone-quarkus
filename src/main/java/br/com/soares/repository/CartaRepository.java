package br.com.soares.repository;

import br.com.soares.entity.Carta;
import br.com.soares.entity.Classe;
import br.com.soares.entity.Tipo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CartaRepository implements PanacheRepository<Carta> {

    public List<Carta> findBySearch(Long id, String nome, Classe classe, Tipo tipo) {
        return find("nome like :nome OR classe = :classe OR tipo = :tipo OR id = :id",
                Parameters.with("nome", "%" + nome + "%")
                        .and("classe", classe)
                        .and("tipo", tipo)
                        .and("id", id)
                        .map()).list();
    }
}
