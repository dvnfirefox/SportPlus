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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/creation")
    public ObjectNode creation(@RequestBody String json){
        System.out.println(json);
        ObjectNode response = Json.createNode();

        try{
            JsonNode node = Json.toJson(json);
            response = utilisateurService.creez(node.get("nom").toString(), node.get("password").toString());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return  response;
    }

    @PostMapping("supprimer")
    public ObjectNode supprimer(@RequestBody String json){
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            response = utilisateurService.supprimer(node.get("nom").toString());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

}
