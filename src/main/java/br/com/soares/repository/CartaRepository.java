package br.com.soares.repository;

import br.com.soares.entity.Carta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartaRepository implements PanacheRepository<Carta> {
}
