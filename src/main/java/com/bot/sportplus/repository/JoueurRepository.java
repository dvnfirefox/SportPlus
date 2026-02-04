package com.bot.sportplus.repository;

import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Joueur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoueurRepository extends CrudRepository<Joueur,Long> {
    List<Joueur> findByEquipe(Equipe equipe);
    Optional<Joueur> findByEquipeIdAndNumero(Long equipeId, int numero);

}
