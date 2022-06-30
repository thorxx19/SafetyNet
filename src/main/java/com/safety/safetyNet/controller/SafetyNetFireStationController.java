package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetRepository;
import com.safety.safetyNet.service.SafetyNetFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author o.froidefond
 */

@RestController
public class SafetyNetFireStationController {


    @Autowired
    SafetyNetFireStationService safetyNetFireStationService;
    @Autowired
    SafetyNetRepository safetyNetRepository;


    /**
     * retourner une liste des personnes couvertes par la caserne de pompiers.
     *
     * @param stationNumber le numéro de station.
     * @return les habitants couverts par la station numéro
     */
    @GetMapping("/firestation")
    public ResponsePersonsStation getFireStationNumber(@RequestParam int stationNumber) {
        ResponsePersonsStation persons = safetyNetFireStationService.getAllPersonsWithStationNumber(stationNumber);
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
        safetyNetRepository.writeData(listSafety);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une caserne de pompiers en moins.
     *
     * @param deleteFireStations un object de type FireStations.
     */
    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestBody FireStations deleteFireStations) {
        ListSafety listSafety = safetyNetFireStationService.deleteFireStation(deleteFireStations);
        safetyNetRepository.writeData(listSafety);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une caserne de pompiers modifiée.
     *
     * @param putFireStations un object de type FireStations.
     */
    @PutMapping("/firestation")
    public void putFireStation(@RequestBody FireStations putFireStations) {
        ListSafety listSafety = safetyNetFireStationService.putFireStation(putFireStations);
        safetyNetRepository.writeData(listSafety);
    }
}
