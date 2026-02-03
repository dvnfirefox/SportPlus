package com.bot.sportplus.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Partie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pointLocal;
    private int pointVisiteur;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "Tournois_id")
    private Tournois tournois;
    @ManyToOne
    @JoinColumn(name = "equipe_local_id")
    private Equipe equipeLocal;

    @ManyToOne
    @JoinColumn(name = "equipe_visiteur_id")
    private Equipe equipeVisiteur;

    public Tournois getTournois() {
        return tournois;
    }

    public void setTournois(Tournois tournois) {
        this.tournois = tournois;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPointLocal() {
        return pointLocal;
    }

    public void setPointLocal(int pointLocal) {
        this.pointLocal = pointLocal;
    }

    public int getPointVisiteur() {
        return pointVisiteur;
    }

    public void setPointVisiteur(int pointVisiteur) {
        this.pointVisiteur = pointVisiteur;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Equipe getEquipeLocal() {
        return equipeLocal;
    }

    public void setEquipeLocal(Equipe equipeLocal) {
        this.equipeLocal = equipeLocal;
    }

    public Equipe getEquipeVisiteur() {
        return equipeVisiteur;
    }

    public void setEquipeVisiteur(Equipe equipeVisiteur) {
        this.equipeVisiteur = equipeVisiteur;
    }
}
