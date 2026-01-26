package com.bot.sportplus.service;


import com.bot.sportplus.model.Officiel;
import com.bot.sportplus.model.Utilisateur;
import com.bot.sportplus.repository.OfficielRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficielService {
    @Autowired
    private OfficielRepository officielRepository;

    public ObjectNode creez(String nom, String phone, String courriel, String adresse, String federation, String role) {
        ObjectNode response = Json.createNode();
        Officiel officiel = new Officiel();
        officiel.setNom(nom);
        officiel.setNumeroTelephone(phone);
        officiel.setCourriel(courriel);
        officiel.setAdresse(adresse);
        officiel.setFederation(federation);
        officiel.setRole(role);
        officielRepository.save(officiel);


        response.put("message", "utilisateur creez avec success.");
        response.put("status", Boolean.TRUE);
        return response;
    }
}
