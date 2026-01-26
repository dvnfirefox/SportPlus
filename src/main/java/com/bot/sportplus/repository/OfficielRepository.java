package com.bot.sportplus.repository;


import com.bot.sportplus.model.Officiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficielRepository extends JpaRepository<Officiel,Integer> {
    Optional<Officiel> findByNomContainingIgnoreCase(String nom);
}
