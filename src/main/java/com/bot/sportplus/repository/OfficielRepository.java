package com.bot.sportplus.repository;


import com.bot.sportplus.model.Officiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficielRepository extends JpaRepository<Officiel,Integer> {
    Optional<Officiel> findByNomContainingIgnoreCase(String nom);
    List<Officiel> findOfficielByNomContainingIgnoreCase(String nom);
    List<Officiel> findOfficielBynumeroTelephoneContainingIgnoreCase(String nom);
    List<Officiel> findOfficielByCourrielContainingIgnoreCase(String nom);
    List<Officiel> findOfficielByAdresseContainingIgnoreCase(String nom);
    List<Officiel> findOfficielByRoleContainingIgnoreCase(String nom);

}





