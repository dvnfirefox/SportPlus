package com.bot.sportplus.controller;

import com.bot.sportplus.service.OfficielService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/officiel")
public class OfficielController {
    @Autowired
    private OfficielService officielService;

    @PostMapping("/creez")
    public String creation(@RequestBody String json){
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            response = officielService.creez(node.get("nom").asText(), node.get("phone").asText(), node.get("courriel").asText(), node.get("adresse").asText(), node.get("federation").asText(), node.get("role").asText());
        }catch (Exception e){
            response.put("status",e.getMessage());
        }
        System.out.println(response);
        return response.toString();
    }

    @GetMapping("/recherche")
    public List<String> recherche(
            @RequestParam String keyword,
            @RequestParam String mode
    ) {
        try {
            return officielService.recherche(keyword, mode);
        } catch (Exception e) {
            return List.of(); // liste vide en cas d'erreur
        }
    }
    @PostMapping("/supprimer")
    public ObjectNode supprimer(@RequestBody String json){
        //recoit les demandes supression et les effectues
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            response = officielService.supprimer(node.get("id").asText());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        System.out.println(response);
        return response;
    }

}
