package com.bot.sportplus.service;

import com.bot.sportplus.model.Federation;
import com.bot.sportplus.repository.FederationRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FederationService {
    @Autowired
    private FederationRepository federationRepository;

    public boolean creez(String nom){
        if(federationRepository.findByNom(nom).isEmpty()){
            Federation federation = new Federation();
            federation.setNom(nom);
            federationRepository.save(federation);
            return true;
        }
        return false;
    }
    public List<String> listeFederations(){
        List<Federation> federations = federationRepository.findAll();
        List<String> listeFederations = new ArrayList<>();
        for(Federation federation : federations){
            listeFederations.add(federation.getNom());
        }
        return listeFederations;
    }
}
