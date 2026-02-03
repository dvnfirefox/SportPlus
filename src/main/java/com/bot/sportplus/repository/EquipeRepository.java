package com.bot.sportplus.repository;

import com.bot.sportplus.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    boolean existsByNom(String nom);
}
