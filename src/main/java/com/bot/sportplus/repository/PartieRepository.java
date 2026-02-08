package com.bot.sportplus.repository;


import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PartieRepository extends JpaRepository<Partie, Long> {

    List<Partie> findByDate(LocalDate date);
    List<Partie> findByDateAfter(LocalDate start);
    List<Partie> findByDateBefore(LocalDate start);


    // Add these:
    List<Partie> findByTournoisId(Long tournoislId);
    List<Partie> findByEquipeLocalNomIgnoreCase(String nom);
    List<Partie> findByEquipeVisiteurNomIgnoreCase(String nom);
}
