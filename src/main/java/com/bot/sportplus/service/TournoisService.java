package com.bot.sportplus.service;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import com.bot.sportplus.model.Tournois;
import com.bot.sportplus.repository.EquipeRepository;
import com.bot.sportplus.repository.TournoisRepository;
import com.bot.sportplus.tools.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TournoisService {

    @Autowired
    private TournoisRepository tournoisRepository;
    @Autowired
    private EquipeRepository equipeRepository;

    /** Crée un tournoi */
    public boolean creez(LocalDate dateDebut, LocalDate dateFin, String categorie, String federation, int maximum) {
        Tournois tournois = new Tournois();
        tournois.setDateDebut(dateDebut);
        tournois.setDateFin(dateFin);
        tournois.setCategorie(categorie);
        tournois.setFederation(federation);
        tournois.setEquipeMaximum(maximum);
        tournoisRepository.save(tournois);
        return true;
    }

    /** Supprime un tournoi par ID */
    public ObjectNode supprimer(String id) {
        ObjectNode response = Json.createNode();
        Optional<Tournois> tournoi = tournoisRepository.findById(Long.valueOf(id));
        if (tournoi.isPresent()) {
            tournoisRepository.delete(tournoi.get());
            response.put("message", "Le tournoi a été supprimé.");
        } else {
            response.put("message", "Le tournoi n'existe pas.");
        }
        return response;
    }

    public boolean modifier(long id, LocalDate dateDebut, LocalDate dateFin, String categorie, String federation, int maximum){
        Optional<Tournois> tournoi = tournoisRepository.findById(id);
        if (tournoi.isPresent()) {
            tournoi.get().setDateDebut(dateDebut);
            tournoi.get().setDateFin(dateFin);
            tournoi.get().setCategorie(categorie);
            tournoi.get().setFederation(federation);
            tournoi.get().setEquipeMaximum(maximum);
            tournoisRepository.save(tournoi.get());
            return true;
        }else  {
            return false;
        }
    }

    @Transactional
    public boolean addEquipe(Equipe equipe, long id) {
        Tournois tournois = tournoisRepository.findById(Long.valueOf(id)).orElse(null);
        if (tournois != null) {
            return false;
        }
        if(tournois.getEquipes().size() >= tournois.getEquipeMaximum()) {
            return false;
        }
        if(tournois.getEquipes().stream().anyMatch(e -> e.getId().equals(equipe.getId()))) {
            return false;
        }
        tournois.getEquipes().add(equipe);
        tournoisRepository.save(tournois);
        return true;
    }

    public boolean removeEquipe(Equipe equipe, long id) {
        Tournois tournois = tournoisRepository.findById(Long.valueOf(id)).orElse(null);
        if (tournois != null) {
            return false;
        }
        if(!tournois.getEquipes().stream().anyMatch(e -> e.getId().equals(equipe.getId()))) {

            return false;
        }
        tournois.getEquipes().remove(equipe);
        tournoisRepository.save(tournois);
        return true;

    }

    public Tournois rechercheId(String id) {
        Optional<Tournois> tournoi = tournoisRepository.findById(Long.valueOf(id));
        if (tournoi.isPresent()) {
            return tournoi.get();
        }else  {
            return null;
        }
    }

    /** Recherche par intervalle de dates */
    public List<Tournois> recherche(LocalDate debut, LocalDate fin, String type) {
        if ("fin".equalsIgnoreCase(type)) {
            return tournoisRepository.findByDateFinBetween(debut, fin);
        } else {
            return tournoisRepository.findByDateDebutBetween(debut, fin);
        }
    }

    /** Recherche par date unique (avant/après) */
    public List<Tournois> recherche(LocalDate date, String type) {
        return switch (type.toLowerCase()) {
            case "fin.after" -> tournoisRepository.findByDateFinAfterOrderByDateDebutDesc(date);
            case "fin.before" -> tournoisRepository.findByDateFinBeforeOrderByDateFinDesc(date);
            case "debut.after" -> tournoisRepository.findByDateDebutAfterOrderByDateDebutDesc(date);
            case "debut.before" -> tournoisRepository.findByDateDebutBeforeOrderByDateFinDesc(date);
            default -> List.of();
        };
    }

    /** Recherche simple par champ et mot-clé pour TableView */
    public List<Tournois> rechercheParChamp(String keyword, String champ) {
        return switch (champ.toLowerCase()) {
            case "id" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> String.valueOf(t.getId()).contains(keyword))
                    .toList();
            case "categorie" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getCategorie().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
            case "federation" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getFederation().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
            case "maximum" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> String.valueOf(t.getEquipeMaximum()).contains(keyword))
                    .toList();
            case "debut" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getDateDebut().toString().contains(keyword))
                    .toList();
            case "fin" -> tournoisRepository.findAll()
                    .stream()
                    .filter(t -> t.getDateFin().toString().contains(keyword))
                    .toList();
            default -> tournoisRepository.findAll();
        };
    }

    public boolean addEquipe(long equipe, Long tournois){
        Optional<Tournois> tournoisOptional = tournoisRepository.findById(tournois);
        Optional<Equipe> equipeOptional = equipeRepository.findById(equipe);
        if (tournoisOptional.isEmpty() || equipeOptional.isEmpty()) {
            return false;
        }
        Tournois t = tournoisOptional.get();
        Equipe e = equipeOptional.get();

        t.getEquipes().add(e);
        e.getTournois().add(t);

        tournoisRepository.save(t);  // ✅ Save the owning side
        return true;
    }

    public boolean removeEquipe(long equipe, Long tournois){
        Optional<Tournois> tournoisOptional = tournoisRepository.findById(tournois);
        Optional<Equipe> equipeOptional = equipeRepository.findById(equipe);
        if (tournoisOptional.isEmpty() || equipeOptional.isEmpty()) {
            return false;
        }
        tournoisOptional.get().getEquipes().remove(equipeOptional.get());
        equipeOptional.get().getTournois().remove(tournoisOptional.get());
        tournoisRepository.save(tournoisOptional.get());
        return true;
    }

    public List<Tournois> tournoisLive(){
        LocalDate now = LocalDate.now();
        return tournoisRepository.findByDateDebutBeforeAndDateFinAfter(now, now);
    }

    public List<Tournois> tournoisFuture(){
        LocalDate now = LocalDate.now();
        return tournoisRepository.findByDateFinAfter(now);
    }

    public Map<String, Integer> Classement(long id){
        Optional<Tournois> tournoisOptional = tournoisRepository.findById(id);

        if (tournoisOptional.isEmpty()) {
            return new LinkedHashMap<>(); // return empty map instead of null
        }

        Tournois tournois = tournoisOptional.get();

        // Map to store team name and their points (wins)
        Map<String, Integer> teamPoints = new HashMap<>();

        // Initialize all teams with 0 points
        for (Equipe equipe : tournois.getEquipes()) {
            teamPoints.put(equipe.getNom(), 0);
        }

        // Loop through all parties and count wins
        for (Partie partie : tournois.getParties()) {
            int pointLocal = partie.getPointLocal();
            int pointVisiteur = partie.getPointVisiteur();

            // Determine winner and add 1 point
            if (pointLocal > pointVisiteur) {
                // Local team won
                String equipeLocalNom = partie.getEquipeLocal().getNom();
                teamPoints.put(equipeLocalNom, teamPoints.get(equipeLocalNom) + 1);
            } else if (pointVisiteur > pointLocal) {
                // Visitor team won
                String equipeVisiteurNom = partie.getEquipeVisiteur().getNom();
                teamPoints.put(equipeVisiteurNom, teamPoints.get(equipeVisiteurNom) + 1);
            }
        }

        // Sort by points descending and return as LinkedHashMap to preserve order
        return teamPoints.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll);
    }
}