package com.dezoo.verblijven.controller;


import com.dezoo.verblijven.model.Verblijf;
import com.dezoo.verblijven.repository.VerblijfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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

    @GetMapping("/verblijven/{verblijfsID}")
    public Verblijf getVerblijfByVerblijfID(@PathVariable String verblijfsID){
        return verblijfRepository.findFirstByVerblijfID(verblijfsID);
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

    @DeleteMapping("/verblijven/verblijfsID")
    public ResponseEntity deleteVerblijf(@PathVariable String verblijfsID){
        Verblijf verblijf = verblijfRepository.findFirstByVerblijfID(verblijfsID);
        if(verblijf!=null){
            verblijfRepository.delete(verblijf);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostConstruct
    public void fillDb(){
        verblijfRepository.deleteAll();
        verblijfRepository.save(new Verblijf("0", "l001", "Pride Rock", 4, 1995, false, "fs161100","l001"));
        verblijfRepository.save(new Verblijf("1", "r001", "De konijnenpijp", 16, 2008, true, "cn170999","r001"));
        verblijfRepository.save(new Verblijf("3", "s001", "Adelaarsnest", 10, 2000, false, "fs161100","s001"));
    }
}
