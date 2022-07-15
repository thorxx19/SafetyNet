package com.safety.safetyNet.service;

import com.safety.safetyNet.model.FireStations;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetFireStationRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;

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


    /**
     * fonction pour récupérer les numéro de téléphone en fonction de la caserne de pompier
     *
     * @param stationNumber numéro de la caserne de pompier
     * @return liste de numéro de téléphone unique
     */
    public TreeSet<String> getNumberPhoneThisFireStation(int stationNumber) {
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);


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

        return treePhone;
    }
}
