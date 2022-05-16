package br.com.soares.repository;

import br.com.soares.entity.Baralho;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BaralhoRepository implements PanacheRepository<Baralho> {
}
