package com.bot.sportplus.repository;

import com.bot.sportplus.model.Tournois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TournoisRepository extends JpaRepository<Tournois, Long> {
    List<Tournois> findByDateDebutBetween(LocalDate start, LocalDate end);
    List<Tournois> findByDateFinBetween(LocalDate start, LocalDate end);
    List<Tournois> findByDateDebutAfter(LocalDate date);
    List<Tournois> findByDateFinAfter(LocalDate date);
    List<Tournois> findByDateDebutBefore(LocalDate date);
    List<Tournois> findByDateFinBefore(LocalDate date);

}
