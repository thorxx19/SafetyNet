package com.safety.safetyNet.controller;


import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetFireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.safety.safetyNet.constantes.SafetyNetConstantes.*;

import java.util.List;

/**
 * @author o.froidefond
 */

@RestController
@Slf4j
public class SafetyNetFireStationController {


    @Autowired
    SafetyNetFireStationService safetyNetFireStationService;
    @Autowired
    SafetyNetWriteFileRepository safetyNetWriteFileRepository;



    /**
     * retourner une liste des personnes couvertes par la caserne de pompiers.
     *
     * @param stationNumber le numéro de station.
     * @return les habitants couverts par la station numéro
     */
    @GetMapping("/firestation")
    public ResponsePersonsStation getFireStationNumber(@RequestParam int stationNumber) {
        ResponsePersonsStation persons = safetyNetFireStationService.getAllPersonsWithStationNumber(stationNumber);
        log.info("Requête reçue -> getFireStationNumber :{}", stationNumber);
        log.info("Objet retourné -> getFireStationNumber :{}", persons);
        return persons;
    }

    /**
     * retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
     * de pompiers la desservant.
     *
     * @param address l'adresse rechercher
     * @return liste d'habitant
     */
    @GetMapping("/fire")
    public ResponsePersonsMedical getHabitantAtThisAdrdress(@RequestParam String address) {
        ResponsePersonsMedical persons = safetyNetFireStationService.getPersonsThisAddressPlusStationNumber(address);
        log.info("Requête reçue -> getHabitantAtThisAdrdress :{}", address);
        log.info("Objet retourné -> getHabitantAtThisAdrdress :{}", persons);
        return persons;
    }

    /**
     * Retourne une liste de tous les foyers desservis par la caserne.
     *
     * @param stations n° de la caserne de pompier
     * @return liste de foyer
     */
    @GetMapping("/flood/stations")
    public List<PersonsFireStation> getHomesAtThisStationNumber(@RequestParam int stations) {
        List<PersonsFireStation> persons = safetyNetFireStationService.getHouseServeFireStation(stations);
        log.info("Requête reçue -> getHomesAtThisStationNumber :{}", stations);
        log.info("Objet retourné -> getHomesAtThisStationNumber :{}", persons);
        return persons;
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une nouvelle caserne de pompiers.
     *
     * @param postFirestations un object de type FireStations.
     */
    @PostMapping("/firestation")
    public void postFireStation(@RequestBody FireStations postFirestations) {
        ListSafety listSafety = safetyNetFireStationService.postNewFireStation(postFirestations);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête reçue -> postFireStation :{}", postFirestations);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une caserne de pompiers en moins.
     *
     * @param deleteFireStations un object de type FireStations.
     */
    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestBody FireStations deleteFireStations) {
        ListSafety listSafety = safetyNetFireStationService.deleteFireStation(deleteFireStations);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête reçue -> deleteFireStation :{}", deleteFireStations);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une caserne de pompiers modifiée.
     *
     * @param putFireStations un object de type FireStations.
     */
    @PutMapping("/firestation")
    public void putFireStation(@RequestBody FireStations putFireStations) {
        ListSafety listSafety = safetyNetFireStationService.putFireStation(putFireStations);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête reçue -> putFireStation :{}", putFireStations);
    }
}
