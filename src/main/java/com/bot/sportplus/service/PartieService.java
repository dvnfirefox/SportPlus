package com.bot.sportplus.service;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.repository.PartieRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PartieService {

    @Autowired
    private PartieRepository partieRepository;


    public boolean creez(Tournois tournois, LocalDate date, int pointVisiteur, int pointLocal, Equipe local, Equipe visiteur){
        Partie partie = new Partie();
        partie.setTournois(tournois);
        partie.setDate(date);
        partie.setPointVisiteur(pointVisiteur);
        partie.setPointLocal(pointLocal);
        partie.setEquipeLocal(local);
        partie.setEquipeVisiteur(visiteur);
        partieRepository.save(partie);
        return true;
    }

    /** Supprime un tournoi par ID */
    public ObjectNode supprimer(String id) {
        ObjectNode response = Json.createNode();
        Optional<Partie> partie = partieRepository.findById(Long.valueOf(id));
        if (partie.isPresent()) {
            partieRepository.delete(partie.get());
            response.put("message", "La partie a été supprimé.");
        } else {
            response.put("message", "La partie n'existe pas.");
        }
        return response;
    }
    public boolean pointage(long id, int visiteur, int local) {
        Optional<Partie> partie = partieRepository.findById(Long.valueOf(id));
        if (partie.isPresent()) {
            partie.get().setPointVisiteur(visiteur);
            partie.get().setPointLocal(local);
            partieRepository.save(partie.get());
            return true;
        }
        return false;

    }
}
