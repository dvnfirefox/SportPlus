package com.bot.sportplus.controller;

import com.bot.sportplus.model.Joueur;
import com.bot.sportplus.service.JoueurService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/joueur")
public class JoueurController {
    @Autowired
    private JoueurService joueurService;

    @PostMapping("creez")
    public boolean creez(@RequestBody String json, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("user") && !role.equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        try {
            JsonNode node = Json.toJson(json);
            String nom = node.get("nom").asText();
            int numero = node.get("numero").asInt();
            long equipeId = node.get("equipeid").asLong();
            return joueurService.creez(nom, numero, equipeId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @PostMapping("supprimer")
    public boolean supprimer(@RequestBody String json, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("user") && !role.equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        try{
            JsonNode node = Json.toJson(json);
            long id  = node.get("id").asLong();
            return joueurService.supprimer(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    @PostMapping("list")
    public List<Joueur> list(@RequestBody String json, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("user") && !role.equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        try{
            JsonNode node = Json.toJson(json);
            long id  = node.get("id").asLong();
            return joueurService.findAllEquipe(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
