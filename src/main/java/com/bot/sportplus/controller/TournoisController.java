package com.bot.sportplus.controller;

import com.bot.sportplus.tools.Json;
import com.bot.sportplus.service.TournoisService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tournois")
public class TournoisController {
    @Autowired
    private TournoisService tournoisService;
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
        }
        
        return response.toString();
    }

    @PostMapping("/supprimer")
    public ObjectNode supprimer(@RequestBody String json){
        //recoit les demandes supression et les effectues
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            response = tournoisService.supprimer(node.get("id").asText());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/recherche/between")
    public List<String> recherche(
            @RequestParam String debut,
            @RequestParam String fin,
            @RequestParam String type
    ) {
        try {
            LocalDateTime dateDebut = LocalDateTime.parse(debut);
            LocalDateTime dateFin = LocalDateTime.parse(fin);
            return tournoisService.recherche(dateDebut, dateFin, type);
        } catch (Exception e) {
            return List.of(); // liste vide en cas d'erreur
        }
    }
    @GetMapping("/recherche")
    public List<String> recherche(
            @RequestParam String date,
            @RequestParam String type
    ) {
        try {
            LocalDateTime datesearch = LocalDateTime.parse(date);
            return tournoisService.recherche(datesearch, type);
        } catch (Exception e) {
            return List.of(); // liste vide en cas d'erreur
        }
    }
}
