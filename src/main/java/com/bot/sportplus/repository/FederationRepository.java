package com.bot.sportplus.repository;

import com.bot.sportplus.model.Federation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FederationRepository extends JpaRepository<Federation,Long> {
    Optional<Federation> findByNom(String nom);

}
