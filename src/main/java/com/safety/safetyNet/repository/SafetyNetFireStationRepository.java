package com.safety.safetyNet.repository;

import com.safety.safetyNet.model.FireStations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Slf4j
public class SafetyNetFireStationRepository {

    @Autowired
    SafetyNetReadFileRepository safetyNetReadFileRepository;

    public List<FireStations> getFireStation(String file){

            return safetyNetReadFileRepository.readfile(file).getFirestations();

    }
}
