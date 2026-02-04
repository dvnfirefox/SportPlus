package com.bot.sportplus.service;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Joueur;
import com.bot.sportplus.repository.EquipeRepository;
import com.bot.sportplus.repository.JoueurRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class JoueurService {
    @Autowired
    private JoueurRepository joueurRepository;
    @Autowired
    private EquipeRepository equipeRepository;

    public boolean creez(String nom, int numero, long equipeId) {

        Optional<Equipe> equipe = equipeRepository.findById(equipeId);
        if(equipe.isPresent()){
            Optional<Joueur> existing = joueurRepository.findByEquipeIdAndNumero(equipeId, numero);
            if(existing.isPresent()){
                return false;
            }
            Joueur joueur = new Joueur();
            joueur.setNom(nom);
            joueur.setNumero(numero);
            joueur.setEquipe(equipe.get());
            joueurRepository.save(joueur);
            return true;
        }
        return false;
    }
    public boolean supprimer(long id) {
        Optional<Joueur> joueur = joueurRepository.findById(id);
        if(joueur.isPresent()){
            joueurRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Joueur> findAllEquipe(long id) {
        Optional<Equipe> equipe = equipeRepository.findById(id);
        return equipe.map(value -> joueurRepository.findByEquipe(value)).orElse(null);
    }
}
