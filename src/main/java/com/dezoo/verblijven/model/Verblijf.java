package com.dezoo.verblijven.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(collection = "verblijven")
public class Verblijf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private String id;
    private String verblijfID;
    private String personeelID;
    private String dierID;
    private String name;
    private Integer maxDieren;
    private Integer bouwJaar;
    private Boolean nocturnal;

    public Verblijf(){

    }

    public Verblijf(String id, String verblijfID, String name, Integer maxDieren, Integer bouwJaar, Boolean nocturnal, String personeelID, String dierID){
        this.id = id;
        this.verblijfID = verblijfID;
        this.name = name;
        this.maxDieren = maxDieren;
        this.bouwJaar = bouwJaar;
        this.nocturnal = nocturnal;
        this.personeelID = personeelID;
        this.dierID = dierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxDieren() {
        return maxDieren;
    }

    public void setMaxDieren(Integer maxDieren) {
        this.maxDieren = maxDieren;
    }

    public Integer getBouwJaar() {
        return bouwJaar;
    }

    public void setBouwJaar(Integer bouwJaar) {
        this.bouwJaar = bouwJaar;
    }

    public Boolean getNocturnal() {
        return nocturnal;
    }

    public void setNocturnal(Boolean nocturnal) {
        this.nocturnal = nocturnal;
    }

    public String getPersoneelID() {
        return personeelID;
    }

    public void setPersoneelID(String personeelID) {
        this.personeelID = personeelID;
    }

    public String getDierID() {
        return dierID;
    }

    public void setDierID(String dierID) {
        this.dierID = dierID;
    }

    public String getVerblijfID() {
        return verblijfID;
    }

    public void setVerblijfID(String verblijfID) {
        this.verblijfID = verblijfID;
    }
}
