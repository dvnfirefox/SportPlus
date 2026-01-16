package com.bot.sportplus.model;


import jakarta.persistence.*;

@Entity
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private int numero;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipes equipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Equipes getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipes equipe) {
        this.equipe = equipe;
    }
}
