package br.com.soares.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Cacheable
@Getter
@Setter
public class Carta {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String nome;
    private String descricao;
    @Range(min = 0, max = 10, message = "O Ataque só pode ser de 0 até 10")
    private Integer ataque;
    @Range(min = 0, max = 10, message = "A Defesa só pode ser de 0 até 10")
    private Integer defesa;
    private Tipo tipo;
    private Classe classe;

    public Carta() {
    }

    public Carta(Long id, String nome, String descricao, Integer ataque, Integer defesa, Tipo tipo, Classe classe) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ataque = ataque;
        this.defesa = defesa;
        this.tipo = tipo;
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Carta carta = (Carta) o;
        return id != null && Objects.equals(id, carta.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
