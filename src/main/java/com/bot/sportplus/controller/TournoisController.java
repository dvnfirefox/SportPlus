package com.bot.sportplus.controller;

import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.service.TournoisService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tournois")
public class TournoisController {

    @Autowired
    private TournoisService tournoisService;

    /** Crée un tournoi */
    @PostMapping("/creez")
    public String creez(@RequestBody String json) {
        ObjectNode response = Json.createNode();
        try {
            JsonNode node = Json.toJson(json);

            LocalDate dateDebut = LocalDate.parse(node.get("datedebut").asText());
            LocalDate dateFin = LocalDate.parse(node.get("datefin").asText());
            String categorie = node.get("categorie").asText();
            String federation = node.get("federation").asText();
            int maximum = node.get("maximum").asInt();

            boolean created = tournoisService.creez(dateDebut, dateFin, categorie, federation, maximum);
            response.put("status", created);
        } catch (Exception e) {
            response.put("status", false);
            response.put("error", e.getMessage());
        }
        return response.toString();
    }

    /** Supprime un tournoi */
    @PostMapping("/supprimer")
    public ObjectNode supprimer(@RequestBody String json) {
        ObjectNode response = Json.createNode();
        try {
            JsonNode node = Json.toJson(json);
            response = tournoisService.supprimer(node.get("id").asText());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    /** Recherche par intervalle de dates */
    @GetMapping("/recherche/between")
    public List<Tournois> rechercheBetween(
            @RequestParam String debut,
            @RequestParam String fin,
            @RequestParam String type
    ) {
        try {
            LocalDate dateDebut = LocalDate.parse(debut);
            LocalDate dateFin = LocalDate.parse(fin);
            return tournoisService.recherche(dateDebut, dateFin, type);
        } catch (Exception e) {
            return List.of(); // liste vide en cas d'erreur
        }
    }

    /** Recherche par date unique */
    @GetMapping("/recherche")
    public List<Tournois> recherche(
            @RequestParam String date,
            @RequestParam String type
    ) {
        try {
            LocalDate datesearch = LocalDate.parse(date);
            return tournoisService.recherche(datesearch, type);
        } catch (Exception e) {
            return List.of(); // liste vide en cas d'erreur
        }
    }

    /** Recherche par champ et mot-clé pour TableView */
    @GetMapping("/recherche/champ")
    public List<Tournois> rechercheParChamp(
            @RequestParam String keyword,
            @RequestParam String champ
    ) {
        try {
            return tournoisService.rechercheParChamp(keyword, champ);
        } catch (Exception e) {
            return List.of();
        }
    }
}
