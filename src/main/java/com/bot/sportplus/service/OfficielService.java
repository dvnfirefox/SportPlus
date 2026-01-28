package com.bot.sportplus.service;


import com.bot.sportplus.model.Officiel;
import com.bot.sportplus.model.Utilisateur;
import com.bot.sportplus.repository.OfficielRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficielService {
    @Autowired
    private OfficielRepository officielRepository;

    public ObjectNode creez(String nom, String phone, String courriel, String adresse,String role) {
        ObjectNode response = Json.createNode();
        Officiel officiel = new Officiel();
        officiel.setNom(nom);
        officiel.setNumeroTelephone(phone);
        officiel.setCourriel(courriel);
        officiel.setAdresse(adresse);
        officiel.setRole(role);
        officielRepository.save(officiel);


        response.put("message", "utilisateur creez avec success.");
        response.put("status", Boolean.TRUE);
        return response;
    }

    public List<String> recherche(String keyword, String mode) {

        List<Officiel> resultats = switch (mode) {
            case "nom" -> officielRepository.findOfficielByNomContainingIgnoreCase(keyword);
            case "numeroTelephone" -> officielRepository.findOfficielBynumeroTelephoneContainingIgnoreCase(keyword);
            case "courriel" -> officielRepository.findOfficielByCourrielContainingIgnoreCase(keyword);
            case "adresse" -> officielRepository.findOfficielByAdresseContainingIgnoreCase(keyword);
            case "role" -> officielRepository.findOfficielByRoleContainingIgnoreCase(keyword);
            default -> List.of();
        };

        // Transforme en "id:nom"
        return resultats.stream()
                .map(o -> o.getId() + "| nom :" + o.getNom() + " courriel :" + o.getCourriel() + " adresse :" + o.getAdresse())
                .toList();
    }
    public ObjectNode supprimer(String id) {
        ObjectNode response = Json.createNode();
        Optional<Officiel> utilisateur = officielRepository.findById(Integer.valueOf(id));
        if(utilisateur.isPresent()) {
            officielRepository.delete(utilisateur.get());
            response.put("message", "L'officiel est supprime.");
        }else{
            response.put("message",  "Le utilisateur n'existe pas.");
        }
        return response;
    }
}
