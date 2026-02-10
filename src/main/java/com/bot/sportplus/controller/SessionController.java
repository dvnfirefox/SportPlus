package com.bot.sportplus.controller;

import com.bot.sportplus.service.EquipeService;
import com.bot.sportplus.service.UtilisateurService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EquipeService equipeService;

    @PostMapping("/connection")
    public String connection(@RequestBody String json, HttpSession session){
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            String nom = node.get("nom").asText();
            String password = node.get("password").asText();
            ObjectNode result = utilisateurService.connection(nom,password);
            if(result.has("connection") && result.get("connection").asBoolean()){
                //mets la session  en memoire
                session.setAttribute("nom", nom);
                session.setAttribute("role",  result.get("role").asText());
                session.setAttribute("connection", true);
                response.put("connection", true);
                if(result.has("equipe")){
                    response.put("equipe", result.get("equipe"));
                    response.put("equipeNom", equipeService.nom(result.get("equipe").asInt()));
                    response.put("equipeFederation", equipeService.federation(result.get("equipe").asInt()));
                    response.put("equipeCategorie", equipeService.categorie(result.get("equipe").asInt()));
                }
            }else
                response.put("connection", false);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        System.out.println(response);
        return response.toString();
    }
    @PostMapping("/connectionadmin")
    public String connectionAdmin(@RequestBody String json, HttpSession session){
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            String nom = node.get("nom").asText();
            String password = node.get("password").asText();
            System.out.println(node.get("nom").asText());
            ObjectNode result = utilisateurService.connection(nom,password);
            if(result.has("connection") && result.get("connection").asBoolean() && result.get("role").asText().equals("admin")){
                //mets la session  en memoire
                session.setAttribute("nom", nom);
                session.setAttribute("role",  result.get("role"));
                session.setAttribute("connection", true);
                response.put("connection", true);
            }else
                response.put("connection", false);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response.toString();
    }
    @PostMapping("/test")
    public String test(HttpSession session) {
        Object roleObj = session.getAttribute("role"); // get the "role" attribute
        if (roleObj != null) {
            return roleObj.toString();  // return the role
        } else {
            return "none"; // or any default value
        }
    }
}

