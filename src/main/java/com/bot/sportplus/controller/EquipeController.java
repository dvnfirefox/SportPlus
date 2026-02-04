package com.bot.sportplus.controller;


import com.bot.sportplus.service.EquipeService;
import com.bot.sportplus.service.UtilisateurService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("equipe")
public class EquipeController {
    @Autowired
    private EquipeService equipeService;
    @Autowired
    private UtilisateurService utilisateurService;

//    private String nom;
//    private String federation;
//    private String categorie;

    @PostMapping("creez")
    public String creez(@RequestBody String json, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("user") && !role.equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            String nom = node.get("nom").asText();
            String federation = node.get("federation").asText();
            String categorie = node.get("categorie").asText();
            String utilisateur = node.get("utilisateur").asText();
            return equipeService.creez(nom,federation,categorie,utilisateur).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        response.put("resultat", false);
        return response.toString();
    }

    @PostMapping("/supprimer")
    public ObjectNode supprimer(@RequestBody String json){
        //recoit les demandes supression et les effectues
        ObjectNode response = Json.createNode();
        try{
            JsonNode node = Json.toJson(json);
            response = equipeService.supprimer(node.get("id").asText());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        System.out.println(response);
        return response;
    }

}
