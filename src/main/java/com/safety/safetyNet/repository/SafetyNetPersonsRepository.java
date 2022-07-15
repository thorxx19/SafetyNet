package com.safety.safetyNet.repository;


import com.safety.safetyNet.model.Persons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Slf4j
public class SafetyNetPersonsRepository {

    @Autowired
    SafetyNetReadFileRepository safetyNetReadFileRepository;


    public List<Persons> getPerson(String file) {

        return safetyNetReadFileRepository.readfile(file).getPersons();
    }
}
