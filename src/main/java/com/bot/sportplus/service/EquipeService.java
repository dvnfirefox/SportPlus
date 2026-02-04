package com.bot.sportplus.service;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.repository.EquipeRepository;
import com.bot.sportplus.repository.PartieRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipeService {
    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private UtilisateurService utilisateurService;

    public ObjectNode creez(String nom, String federation, String categorie, String utilisateur){
        ObjectNode response = Json.createNode();
        if(equipeRepository.existsByNom(nom)){
            response.put("resultat", false);
            return response;
        }
        Equipe equipe = new Equipe();
        equipe.setNom(nom);
        equipe.setFederation(federation);
        equipe.setCategorie(categorie);
        equipeRepository.save(equipe);
        utilisateurService.addEquipe(utilisateur, equipe);
        response.put("resultat", true);
        response.put("equipe", equipe.getId());
        response.put("equipeNom", equipe.getNom());
        return response;
    }

    public ObjectNode supprimer(String id) {
        ObjectNode response = Json.createNode();
        Optional<Equipe> equipe = equipeRepository.findById(Long.valueOf(id));
        if (equipe.isPresent()) {
            equipeRepository.delete(equipe.get());
            response.put("message", "L'equipe a été supprimé.");
        } else {
            response.put("message", "L'equipe n'existe pas.");
        }
        return response;
    }

    public Equipe rechercheId(String id) {
        Optional<Equipe> tournoi = equipeRepository.findById(Long.valueOf(id));
        if (tournoi.isPresent()) {
            return tournoi.get();
        }else  {
            return null;
        }
    }

    public String nom(long id){
        return equipeRepository.findById(id).get().getNom();
    }
}
