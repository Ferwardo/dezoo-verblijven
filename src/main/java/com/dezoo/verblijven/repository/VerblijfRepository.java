package com.dezoo.verblijven.repository;

import com.dezoo.verblijven.model.Verblijf;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerblijfRepository extends MongoRepository<Verblijf, String> {
    List<Verblijf> findVerblijfByPersoneelID(String personeelID);
    List<Verblijf> findVerblijfByDierID(String dierID);
    Verblijf findVerblijfByPersoneelIDAndDierID(String personeelID, String dierID);
    Verblijf findFirstByVerblijfID(String verblijfID);
}
