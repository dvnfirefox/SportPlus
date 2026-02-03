package com.bot.sportplus.service;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Utilisateur;
import com.bot.sportplus.repository.UtilisateurRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public ObjectNode creez(String nom, String password, String role) {
        ObjectNode response = Json.createNode();
        if(utilisateurRepository.findByNom(nom).isPresent()) {
            response.put("message", "le nom dutilisateur existe deja.");
            response.put("status", Boolean.FALSE);
        }else{
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(nom);
            utilisateur.setPassword(password);
            utilisateur.setRole(role);
            utilisateurRepository.save(utilisateur);
            response.put("message", "utilisateur creez avec success.");
            response.put("status", Boolean.TRUE);
        }
        return response;
    }
    public ObjectNode supprimer(String nom) {
        ObjectNode response = Json.createNode();
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByNom(nom);
        if(utilisateur.isPresent()) {
            utilisateurRepository.delete(utilisateur.get());
            response.put("message", "L'utilisateur est supprime.");
        }else{
            response.put("message",  "Le utilisateur n'existe pas.");
        }
        return response;
    }

    public ObjectNode connection(String nom, String password){
        ObjectNode response = Json.createNode();
        Optional<Utilisateur> result = utilisateurRepository.findByNom(nom);
        if(result.isPresent() && result.get().getPassword().equals(password)) {
            response.put("connection", true);
            response.put("message", "connection");
            response.put("role", result.get().getRole());
            response.put("id", result.get().getId());
            if(result.get().getEquipe() != null) {
                response.put("equipe", result.get().getEquipe().getId());
            }else{
                response.put("equipe", 0);
            }
        }else{
            response.put("connection", false);
            response.put("message", "nom ou mot de passe incorrect");
            response.put("admin", false);
        }
        return response;
    }

    public List<String> recherche(String keyword){
        List<Utilisateur> search = utilisateurRepository.findByNomContainingIgnoreCase(keyword);

        // Transforme la liste d'utilisateurs en liste de noms
        List<String> noms = search.stream()
                .map(utilisateur -> utilisateur.getNom().replace("\"", "")) // Supprime les guillemets éventuels
                .toList();

        return noms;
    }
    public void addEquipe(String nom, Equipe equipe) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByNom(nom);
        if(utilisateur.isPresent()) {
            utilisateur.get().setEquipe(equipe);
            utilisateurRepository.save(utilisateur.get());
        }
    }
}

