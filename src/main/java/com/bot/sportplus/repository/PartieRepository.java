package com.bot.sportplus.repository;


import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PartieRepository extends JpaRepository<Partie, Long> {

    List<Partie> findByDateBetween(LocalDate start, LocalDate end);
    List<Partie> findByDateAfter(LocalDate start);
    List<Partie> findByDateBefore(LocalDate start);
    List<Partie> findByEquipeLocal(Equipe equipeLocal);
    List<Partie> findByEquipeVisiteur(Equipe equipeVisiteur);
}
