package com.bot.sportplus.service;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.repository.EquipeRepository;
import com.bot.sportplus.repository.PartieRepository;
import com.bot.sportplus.repository.TournoisRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PartieService {

    @Autowired
    private PartieRepository partieRepository;
    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private TournoisRepository tournoisRepository;


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

    public List<Partie> rechercheDate(String type, LocalDate date){
        System.out.println("Searching with type: " + type + ", date: " + date);
        List<Partie> result = switch (type){
            case "now", "date" -> partieRepository.findByDate(date);
            case "apres" -> partieRepository.findByDateGreaterThanEqual(date);
            case "avant" -> partieRepository.findByDateLessThanEqual(date);
            default -> List.of();
        };
        System.out.println("Found " + result.size() + " parties");
        return result;
    }
    public List<Partie> rechercheDateByEquipe(String type, LocalDate date, long equipe) {
        System.out.println("Searching with type: " + type + ", date: " + date + ", equipe: " + equipe);
        List<Partie> result = switch (type){
            case "now", "date" -> partieRepository.findByDateAndEquipe(date, equipe);
            case "apres" -> partieRepository.findByDateGreaterThanEqualAndEquipe(date, equipe);
            case "avant" -> partieRepository.findByDateLessThanEqualAndEquipe(date, equipe);
            default -> List.of();
        };
        System.out.println("Found " + result.size() + " parties");
        return result;
    }
    public List<Partie> recherche(String type, String text){
        return switch (type.toLowerCase()){
            case "tournois" -> partieRepository.findByTournoisId(Long.parseLong(text));
            case "visiteur" -> partieRepository.findByEquipeVisiteurNomIgnoreCase(text);
            case "local" -> partieRepository.findByEquipeLocalNomIgnoreCase(text);
            default -> List.of();
        };
    }
    public boolean createCalendrier(long tournoisId) {
        Optional<Tournois> tournois = tournoisRepository.findByIdWithEquipes(tournoisId);

        if (tournois.isEmpty()) {
            System.out.println("tournois empty");
            return false;
        }
        if(tournois.get().getEquipes().isEmpty()){
            System.out.println("equipe empty");
            return false;
        }
        LocalDate debut = tournois.get().getDateDebut();
        LocalDate fin = tournois.get().getDateFin();

        List<LocalDate> dureeTournois = debut.datesUntil(fin.plusDays(1)).toList();
        int dateIndex = 0;
        int dateTotal  = dureeTournois.size();
        List<Equipe> equipes = tournois.get().getEquipes();


        for (int i = 0; i < equipes.size(); i++) {
            for(int j = i + 1; j < equipes.size(); j++) {
                Partie partie = new Partie();
                partie.setEquipeLocal(equipes.get(i));
                partie.setEquipeVisiteur(equipes.get(j));
                partie.setTournois(tournois.get());
                partie.setDate(dureeTournois.get(dateIndex));
                System.out.println(partie);
                partieRepository.save(partie);
                dateIndex++;
                if(dateIndex >= dateTotal){
                    dateIndex = 0;
                }


            }
        }
        return true;
    }
    public List<Partie> futurePartie(long id) {
        List<Partie> local = partieRepository.findByEquipeLocalId(id);
        List<Partie> visiteur = partieRepository.findByEquipeVisiteurId(id);

        List<Partie> combined = new ArrayList<>(local);
        combined.addAll(visiteur);
        return combined;
    }
}
