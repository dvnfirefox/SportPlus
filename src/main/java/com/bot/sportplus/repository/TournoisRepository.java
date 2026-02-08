package com.bot.sportplus.repository;

import com.bot.sportplus.model.Tournois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TournoisRepository extends JpaRepository<Tournois, Long> {
    List<Tournois> findByDateDebutBetween(LocalDate start, LocalDate end);
    List<Tournois> findByDateFinBetween(LocalDate start, LocalDate end);
    List<Tournois> findByDateDebutAfter(LocalDate date);
    List<Tournois> findByDateFinAfter(LocalDate date);
    List<Tournois> findByDateDebutBefore(LocalDate date);
    List<Tournois> findByDateFinBefore(LocalDate date);
    List<Tournois> findByDateFinAfterOrderByDateDebutDesc(LocalDate date);
    List<Tournois> findByDateFinBeforeOrderByDateFinDesc(LocalDate date);
    List<Tournois> findByDateDebutAfterOrderByDateDebutDesc(LocalDate date);
    List<Tournois> findByDateDebutBeforeOrderByDateFinDesc(LocalDate date);
    List<Tournois> findByDateDebutBeforeAndDateFinAfter(LocalDate now1, LocalDate now2);
    @Query("SELECT t FROM Tournois t LEFT JOIN FETCH t.equipes WHERE t.id = :id")
    Optional<Tournois> findByIdWithEquipes(@Param("id") Long id);

}
