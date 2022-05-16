package br.com.soares.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cacheable
@Getter
@Setter
@EqualsAndHashCode
public class Baralho {
    @Id
    @GeneratedValue
    public Long id;
    public String nome;
    @ManyToMany
    public Set<Carta> cartas = new HashSet<>();

    public Baralho() {
    }

    public Baralho(Long id, String nome, Set<Carta> cartas) {
        this.id = id;
        this.nome = nome;
        this.cartas = cartas;
    }
}
