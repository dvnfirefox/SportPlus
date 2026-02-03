package com.bot.sportplus.controller;

import com.bot.sportplus.model.Utilisateur;
import com.bot.sportplus.service.UtilisateurService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/creez")
    public String creation(@RequestBody String json){
        ObjectNode response = Json.createNode();
        System.out.println(json.toString());
        try{
            JsonNode node = Json.toJson(json);
            response = utilisateurService.creez(node.get("nom").asText(), node.get("password").asText(), node.get("admin").asText());
        } catch (Exception e) {
            response.put("message", e.getMessage());
            System.out.println(e.getMessage());
        }
        return  response.toString();
    }

    @PostMapping("supprimer")
    public ObjectNode supprimer(@RequestBody String json){
        //recoit les demandes supression et les effectues
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            response = utilisateurService.supprimer(node.get("nom").asText());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    //api endpoint pour faire une recherche d'utilisateur
    @GetMapping("recherche")
    public List<String> recherche(@RequestParam String keyword){
        try{
            return utilisateurService.recherche(keyword);
        }catch (Exception e){
            return List.of(); // retourne une liste vide en cas d'erreur
        }
    }
}

