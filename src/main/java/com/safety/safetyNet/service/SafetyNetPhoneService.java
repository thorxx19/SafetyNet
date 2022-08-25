package com.safety.safetyNet.service;

import com.safety.safetyNet.configuration.SafetyNetConfiguration;
import com.safety.safetyNet.model.FireStations;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetFireStationRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;


/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetPhoneService {
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetFireStationRepository safetyNetFireStationRepository;
    @Autowired
    SafetyNetConfiguration safetyNetConfiguration;


    /**
     * fonction pour récupérer les numéro de téléphone en fonction de la caserne de pompier
     *
     * @param stationNumber numéro de la caserne de pompier
     * @return liste de numéro de téléphone unique
     */
    public TreeSet<String> getNumberPhoneByFireStation(int stationNumber) {
        log.debug("Start getNumberPhoneByFireStation");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);


        TreeSet<String> treePhone = new TreeSet<>();

        for (FireStations firestation : dataFireStations) {
            int station = Integer.parseInt(firestation.getStation());
            if (stationNumber == station) {
                for (Persons person : dataPersons) {
                    if (person.getAddress().equals(firestation.getAddress())) {
                        treePhone.add(person.getPhone());
                    }
                }
            }
        }
        log.debug("End getNumberPhoneByFireStation");
        return treePhone;
    }
}
