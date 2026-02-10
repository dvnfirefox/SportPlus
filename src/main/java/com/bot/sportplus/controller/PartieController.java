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
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("partie")
public class PartieController {
    @Autowired
    private PartieService partieService;
    @Autowired
    private TournoisService tournoisService;
    @Autowired
    private EquipeService equipeService;

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
    @PostMapping("/calendrier")
    public boolean calendrierPar(@RequestBody String json) {
        try{
            JsonNode node = Json.toJson(json);
            long id =  node.get("id").asLong();
            return partieService.createCalendrier(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @GetMapping("/recherchedate")
    public List<Partie> rechercherDate(
            @RequestParam String type,
            @RequestParam String date,
            @RequestParam(required = false) String equipe
    ){
        try {
            LocalDate date1 = LocalDate.parse(date);
            if (equipe != null && !equipe.isEmpty() && !equipe.equals("0")) {
                // Appeler une méthode de service qui filtre par équipe
                return partieService.rechercheDateByEquipe(type, date1, Long.parseLong(equipe));
            } else {
                // Appeler la méthode existante sans filtre d'équipe
                return partieService.rechercheDate(type, date1);
            }
        } catch (Exception e) {
            return List.of(); // liste vide en cas d'erreur
        }
    }

    @GetMapping("futurePartie")
    public List<Partie> futurePartie(@RequestParam String equipe){
        try{
            long id  = Long.parseLong(equipe);
            return partieService.futurePartie(id);
        }catch (Exception e){
            return List.of();
        }
    }

    @GetMapping("recherche")
    public List<Partie> recherchePartie(
            @RequestParam String type,
            @RequestParam String text
    ){
        return partieService.recherche(type, text);
    }
    @PostMapping("/pointage")
    public boolean pointagePar(@RequestBody String json) {
        try {
            JsonNode node = Json.toJson(json);
            long id = node.get("id").asLong();
            int pointLocal = node.get("pointLocal").asInt();
            int pointVisiteur = node.get("pointVisiteur").asInt();
            return partieService.pointage(id, pointVisiteur, pointLocal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
