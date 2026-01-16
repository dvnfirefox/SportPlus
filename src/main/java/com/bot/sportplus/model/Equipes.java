package com.bot.sportplus.model;


import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Equipes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String federation; //TODO
    private String categorie;
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Joueur> joueurs = new ArrayList<>();
    @OneToMany(mappedBy = "equipeLocal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partie> partieLocal = new ArrayList<>();
    @OneToMany(mappedBy = "equipeVisiteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partie> partieVisiteur = new ArrayList<>();

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

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
}
