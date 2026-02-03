package com.bot.sportplus.controller;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.service.EquipeService;
import com.bot.sportplus.service.PartieService;
import com.bot.sportplus.service.TournoisService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("partie")
public class PartieController {
    @Autowired
    private PartieService partieService;
    @Autowired
    private TournoisService tournoisService;
    @Autowired
    private EquipeService equipeService;

    @PostMapping("creez")
    public String creez(@RequestBody String json){
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            Tournois tournois = tournoisService.rechercheId(node.get("tournois").asText());
            LocalDate date = LocalDate.parse(node.get("date").asText());
            int pointLocal = node.get("localpoint").asInt();
            int pointVisiteur = node.get("visiteurpoint").asInt();
            Equipe local = equipeService.rechercheId(node.get("equipe").asText());
            Equipe visiteur = equipeService.rechercheId(node.get("equipe").asText());
            boolean resultat = partieService.creez(tournois, date, pointLocal, pointVisiteur, local, visiteur);
            response.put("status", resultat);
        }catch (Exception e){
            System.out.println(e.getMessage());
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
            response = partieService.supprimer(node.get("id").asText());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        System.out.println(response);
        return response;
    }
}
