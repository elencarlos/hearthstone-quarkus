package br.com.soares.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Cacheable
@Getter
@Setter
public class Carta {
    @Id
    @GeneratedValue
    public Long id;
    @NotBlank
    public String nome;
    public String descricao;
    @Range(min = 0, max = 10, message = "O Ataque só pode ser de 0 até 10")
    public Integer ataque;
    @Range(min = 0, max = 10, message = "A Defesa só pode ser de 0 até 10")
    public Integer defesa;
    public Tipo tipo;
    public Classe classe;

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
}
