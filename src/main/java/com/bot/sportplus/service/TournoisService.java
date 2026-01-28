package com.bot.sportplus.service;

import com.bot.sportplus.model.Officiel;
import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.repository.TournoisRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jdi.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournoisService {
    @Autowired
    private TournoisRepository tournoisRepository;

    public boolean creez(LocalDate dateDebut, LocalDate dateFin, String categorie, String federation, int maximum){
        Tournois tournois = new Tournois();
        tournois.setDateDebut(dateDebut);
        tournois.setDateFin(dateFin);
        tournois.setCategorie(categorie);
        tournois.setFederation(federation);
        tournois.setEquipeMaximum(maximum);
        tournoisRepository.save(tournois);
        return true;
    }
    public ObjectNode supprimer(String id){
        ObjectNode response = Json.createNode();
        Optional<Tournois> utilisateur = tournoisRepository.findById(Long.valueOf(id));
        if(utilisateur.isPresent()) {
            tournoisRepository.delete(utilisateur.get());
            response.put("message", "L'officiel est supprime.");
        }else{
            response.put("message",  "Le utilisateur n'existe pas.");
        }
        return response;
    }

    public List<String> recherche(LocalDateTime debut, LocalDateTime fin,String type){
        List<Tournois> tournois;
        if(type.equals("fin")){
            tournois = tournoisRepository.findByDateFinBetween(debut,fin);
        }else{
            tournois = tournoisRepository.findByDateDebutBetween(debut,fin);
        }
        return  tournois.stream().map(Tournois::getCategorie).collect(Collectors.toList());
    }
    public List<String> recherche(LocalDateTime date, String type) {
        List<Tournois> tournois = switch (type.toLowerCase()) {
            case "fin.after" -> tournoisRepository.findByDateFinAfter(date);
            case "fin.before" -> tournoisRepository.findByDateFinBefore(date);
            case "debut.after" -> tournoisRepository.findByDateDebutAfter(date);
            case "debut.before" -> tournoisRepository.findByDateDebutBefore(date);
            default -> List.of();
        };

        return tournois.stream()
                .map(Tournois::getCategorie)
                .collect(Collectors.toList());
    }

}