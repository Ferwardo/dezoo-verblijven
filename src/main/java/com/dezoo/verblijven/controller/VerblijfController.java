package com.dezoo.verblijven.controller;


import com.dezoo.verblijven.model.Verblijf;
import com.dezoo.verblijven.repository.VerblijfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VerblijfController {

    @Autowired
    private VerblijfRepository verblijfRepository;

    @GetMapping("/verblijven/personeel/{personeelID}")
    public List<Verblijf> getVerblijvenByPersoneelID(@PathVariable String personeelID){
        return verblijfRepository.findVerblijfByPersoneelID(personeelID);
    }

    @GetMapping("/verblijven/dieren/{dierID}")
    public List<Verblijf> getVerblijvenByDierID(@PathVariable String dierID){
        return verblijfRepository.findVerblijfByDierID(dierID);
    }

    @GetMapping("/verblijven/personeel/{personeelID}/dieren/{dierID}")
    public Verblijf getVerblijfByPersoneelIDAndDierID(@PathVariable String personeelID, @PathVariable String dierID){
        return verblijfRepository.findVerblijfByPersoneelIDAndDierID(personeelID, dierID);
    }

    @PostMapping("/verblijven")
    public Verblijf addVerblijf(@RequestBody Verblijf verblijf){
        verblijfRepository.save(verblijf);
        return verblijf;
    }

    @PutMapping("/verblijven")
    public Verblijf updateVerblijf(@RequestBody Verblijf updatedVerblijf){
        Verblijf retrievedVerblijf = verblijfRepository.findVerblijfByPersoneelIDAndDierID(updatedVerblijf.getPersoneelID(), updatedVerblijf.getDierID());

        retrievedVerblijf.setBouwJaar(updatedVerblijf.getBouwJaar());
        retrievedVerblijf.setMaxDieren(updatedVerblijf.getMaxDieren());
        retrievedVerblijf.setName(updatedVerblijf.getName());
        retrievedVerblijf.setNocturnal(updatedVerblijf.getNocturnal());

        verblijfRepository.save(retrievedVerblijf);
        return retrievedVerblijf;
    }

    @DeleteMapping("/verblijven/personeel/{personeelID}/dieren/{dierID}")
    public ResponseEntity deleteVerblijf(@PathVariable String personeelID, @PathVariable String dierID){
        Verblijf verblijf = verblijfRepository.findVerblijfByPersoneelIDAndDierID(personeelID, dierID);
        if(verblijf!=null){
            verblijfRepository.delete(verblijf);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}