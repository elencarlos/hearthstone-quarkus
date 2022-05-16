package br.com.soares.repository;

import br.com.soares.entity.Baralho;
import br.com.soares.entity.Classe;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class BaralhoRepository implements PanacheRepository<Baralho> {

    public List<Baralho> findBySearch(Long id, String nome, Classe classe) {
        return find("select distinct baralho from Baralho baralho " +
                "INNER JOIN baralho.cartas AS cartas " +
                "where cartas.nome like :nome " +
                "OR cartas.classe = :classe " +
                "OR baralho.id = :id",
                Parameters.with("nome", "%" + nome + "%")
                        .and("classe", classe)
                        .and("id", id)
                        .map()).list();
    }
}
