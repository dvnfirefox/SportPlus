package com.bot.sportplus.repository;

import com.bot.sportplus.model.Tournois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TournoisRepository extends JpaRepository<Tournois, Long> {
    List<Tournois> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);
    List<Tournois> findByDateFinBetween(LocalDateTime start, LocalDateTime end);
    List<Tournois> findByDateDebutAfter(LocalDateTime date);
    List<Tournois> findByDateFinAfter(LocalDateTime date);
    List<Tournois> findByDateDebutBefore(LocalDateTime date);
    List<Tournois> findByDateFinBefore(LocalDateTime date);

}
