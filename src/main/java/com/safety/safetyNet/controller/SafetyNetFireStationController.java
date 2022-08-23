package com.safety.safetyNet.controller;


import com.safety.safetyNet.model.ResponsePersonsByStationNumber;
import com.safety.safetyNet.model.ResponseFireStationByNumber;
import com.safety.safetyNet.model.FireStations;
import com.safety.safetyNet.model.ResponsePersonsByAddress;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetFireStationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * @author o.froidefond
 */

@RestController
@CrossOrigin("http://localhost:3000/")
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
    public ResponseEntity<?> getFireStationByNumber(@RequestParam int stationNumber) {
        List<ResponseFireStationByNumber> persons = safetyNetFireStationService
                .getAllPersonsByStationNumber(stationNumber);
        if (persons.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(persons);
        } else {
           return ResponseEntity.status(HttpStatus.OK).body(persons);
        }
    }

    /**
     * retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
     * de pompiers la desservant.
     *
     * @param address l'adresse rechercher
     * @return liste d'habitant
     */
    @GetMapping("/fire")
    public ResponseEntity<?> getGroupOfPersonsByAddress(@RequestParam String address) {
        List<ResponsePersonsByAddress> persons = safetyNetFireStationService.getPersonsByAddress(address);
        if (persons.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(persons);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(persons);
        }
    }

    /**
     * Retourne une liste de tous les foyers desservis par la caserne.
     *
     * @param stations n° de la caserne de pompier
     * @return liste de foyer
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<?> getPersonsCardsByStationNumber(@RequestParam String stations) {
        List<ResponsePersonsByStationNumber> persons = safetyNetFireStationService.getPersonsByStationNumber(stations);
        if (persons.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(persons);
        } else {
           return ResponseEntity.status(HttpStatus.OK).body(persons);
        }
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une nouvelle caserne de pompiers.
     *
     * @param postFirestations un object de type FireStations.
     * @return http status
     */
    @PostMapping("/firestation")
    public ResponseEntity<?> postFireStation(@RequestBody FireStations postFirestations) {
        ListSafety listSafety = safetyNetFireStationService.postNewFireStation(postFirestations);
        safetyNetWriteFileRepository.writeData(listSafety);
        return ResponseEntity.status(HttpStatus.CREATED).body(Strings.EMPTY);
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
    }
}
