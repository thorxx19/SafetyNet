package com.safety.safetyNet.service;

import com.safety.safetyNet.model.ResponsePersonsFireStation;
import com.safety.safetyNet.model.ResponsePersonsMedical;
import com.safety.safetyNet.model.ResponsePersonsStation;
import com.safety.safetyNet.controller.SafetyNetFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author o.froidefond
 */

@RestController
public class SafetyNetFireStationController {


    @Autowired
    SafetyNetFireStationService safetyNetFireStationService;


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
     * retourner une liste de tous les foyers desservis par la caserne
     *
     * @param stations n° de la caserne de pompier
     * @return liste de foyer
     */
    @GetMapping("/flood/stations")
    public ResponsePersonsFireStation getHomesAtThisStationNumber(@RequestParam int stations) {
        ResponsePersonsFireStation persons = safetyNetFireStationService.getHouseServeFireStation(stations);
        return persons;
    }
}
