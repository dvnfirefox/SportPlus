package com.bot.sportplus.model;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
public class Partie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int point;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "equipe_local_id")
    private Equipes equipeLocal;

    @ManyToOne
    @JoinColumn(name = "equipe_visiteur_id")
    private Equipes equipeVisiteur;
}
