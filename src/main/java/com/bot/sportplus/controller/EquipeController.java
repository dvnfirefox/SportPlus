package com.bot.sportplus.controller;


import com.bot.sportplus.service.EquipeService;
import com.bot.sportplus.service.UtilisateurService;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean creez(@RequestBody String json){
        System.out.println(json.toString());
        try{
            JsonNode node = Json.toJson(json);
            String nom = node.get("nom").asText();
            String federation = node.get("federation").asText();
            String categorie = node.get("categorie").asText();
            String utilisateur = node.get("utilisateur").asText();
            boolean resultat = equipeService.creez(nom,federation,categorie,utilisateur);
            System.out.println("resultat = "+resultat);
            return resultat;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
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
