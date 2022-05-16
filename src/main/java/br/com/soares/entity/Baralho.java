package br.com.soares.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Cacheable
@Getter
@Setter
public class Baralho {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    @ManyToMany
    private Set<Carta> cartas = new HashSet<>();

    public Baralho() {
    }

    public Baralho(Long id, String nome, Set<Carta> cartas) {
        this.id = id;
        this.nome = nome;
        this.cartas = cartas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Baralho baralho = (Baralho) o;
        return id != null && Objects.equals(id, baralho.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
