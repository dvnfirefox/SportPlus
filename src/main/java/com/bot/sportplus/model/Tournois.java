package com.bot.sportplus.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tournois {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String categorie;
    private String federation;
    private int equipeMaximum;
    @ManyToMany
    @JoinTable(
            name = "participant_equipe",
            joinColumns = @JoinColumn(name = "tournois_id"),
            inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private List<Equipes> equipes = new ArrayList<>(); // Initialize the list



}
