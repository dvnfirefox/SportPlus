package com.bot.sportplus.service;

import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.repository.TournoisRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TournoisService {

    @Autowired
    private TournoisRepository tournoisRepository;

    /** Crée un tournoi */
    public boolean creez(LocalDate dateDebut, LocalDate dateFin, String categorie, String federation, int maximum) {
        Tournois tournois = new Tournois();
        tournois.setDateDebut(dateDebut);
        tournois.setDateFin(dateFin);
        tournois.setCategorie(categorie);
        tournois.setFederation(federation);
        tournois.setEquipeMaximum(maximum);
        tournoisRepository.save(tournois);
        return true;
    }

    /** Supprime un tournoi par ID */
    public ObjectNode supprimer(String id) {
        ObjectNode response = Json.createNode();
        Optional<Tournois> tournoi = tournoisRepository.findById(Long.valueOf(id));
        if (tournoi.isPresent()) {
            tournoisRepository.delete(tournoi.get());
            response.put("message", "Le tournoi a été supprimé.");
        } else {
            response.put("message", "Le tournoi n'existe pas.");
        }
        return response;
    }

    /** Recherche par intervalle de dates */
    public List<Tournois> recherche(LocalDate debut, LocalDate fin, String type) {
        if ("fin".equalsIgnoreCase(type)) {
            return tournoisRepository.findByDateFinBetween(debut, fin);
        } else {
            return tournoisRepository.findByDateDebutBetween(debut, fin);
        }
    }

    /** Recherche par date unique (avant/après) */
    public List<Tournois> recherche(LocalDate date, String type) {
        return switch (type.toLowerCase()) {
            case "fin.after" -> tournoisRepository.findByDateFinAfter(date);
            case "fin.before" -> tournoisRepository.findByDateFinBefore(date);
            case "debut.after" -> tournoisRepository.findByDateDebutAfter(date);
            case "debut.before" -> tournoisRepository.findByDateDebutBefore(date);
            default -> List.of();
        };
    }

    /** Recherche simple par champ et mot-clé pour TableView */
    public List<Tournois> rechercheParChamp(String keyword, String champ) {
        return switch (champ.toLowerCase()) {
            case "id" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> String.valueOf(t.getId()).contains(keyword))
                    .toList();
            case "categorie" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getCategorie().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
            case "federation" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getFederation().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
            case "maximum" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> String.valueOf(t.getEquipeMaximum()).contains(keyword))
                    .toList();
            case "debut" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getDateDebut().toString().contains(keyword))
                    .toList();
            case "fin" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getDateFin().toString().contains(keyword))
                    .toList();
            default -> tournoisRepository.findAll();
        };
    }
}
