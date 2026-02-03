package com.bot.sportplus.controller;

import com.bot.sportplus.model.Federation;
import com.bot.sportplus.service.FederationService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/federation")
public class FederationController {
    @Autowired
    private FederationService federationService;

    @PostMapping("/creez")
    public boolean creez(@RequestBody String json){
        String nom;
        try{
            JsonNode node = Json.toJson(json);
            nom = node.get("nom").asText();
            System.out.println(federationService.creez(nom));
            return federationService.creez(nom);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    @GetMapping("/list")
    public List<String> list(){
        return federationService.listeFederations();
    }

}
