package com.bot.sportplus.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String federation;
    private String categorie;

    @OneToMany(mappedBy = "equipe")
    private List<Joueur> joueurs;


    @ManyToMany(mappedBy = "equipes")
    @JsonIgnoreProperties({"equipes", "parties"})
    private List<Tournois> tournois;

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public List<Tournois> getTournois() {
        return tournois;
    }

    public void setTournois(List<Tournois> tournois) {
        this.tournois = tournois;
    }

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

    public String getFederation() {
        return federation;
    }

    public void setFederation(String federation) {
        this.federation = federation;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
