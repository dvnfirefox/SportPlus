package com.bot.sportplus.repository;


import com.bot.sportplus.model.Equipe;
import com.bot.sportplus.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PartieRepository extends JpaRepository<Partie, Long> {

    List<Partie> findByDate(LocalDate date);
    List<Partie> findByDateGreaterThanEqual(LocalDate date);
    List<Partie> findByDateLessThanEqual(LocalDate date);
    @Query("SELECT p FROM Partie p WHERE p.date = :date AND (p.equipeLocal.id = :equipeId OR p.equipeVisiteur.id = :equipeId)")
    List<Partie> findByDateAndEquipe(@Param("date") LocalDate date, @Param("equipeId") long equipeId);

    @Query("SELECT p FROM Partie p WHERE p.date >= :date AND (p.equipeLocal.id = :equipeId OR p.equipeVisiteur.id = :equipeId)")
    List<Partie> findByDateGreaterThanEqualAndEquipe(@Param("date") LocalDate date, @Param("equipeId") long equipeId);

    @Query("SELECT p FROM Partie p WHERE p.date <= :date AND (p.equipeLocal.id = :equipeId OR p.equipeVisiteur.id = :equipeId)")
    List<Partie> findByDateLessThanEqualAndEquipe(@Param("date") LocalDate date, @Param("equipeId") long equipeId);


    // Add these:
    List<Partie> findByTournoisId(Long tournoislId);
    List<Partie> findByEquipeLocalNomIgnoreCase(String nom);
    List<Partie> findByEquipeVisiteurNomIgnoreCase(String nom);
    List<Partie> findByEquipeLocalId(Long id);
    List<Partie> findByEquipeVisiteurId(Long id);
}
