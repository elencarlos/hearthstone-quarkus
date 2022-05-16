package br.com.soares.entity;

import java.util.HashSet;
import java.util.Set;

public class Baralho {
    public Long id;
    Set<Carta> cartas = new HashSet<>();
}
