package com.safety.safetyNet.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author o.froidefond
 */
@RestController
public class SafetyController {


    /**
     * retourner une liste des personnes couvertes par la caserne de pompiers.
     * @param stationNumber le numéro de station.
     * @return les habitants couverts par la station numéro
     */
    @GetMapping("/firestation")
    public String getFireStationNumber(@RequestParam int stationNumber){
        String message = "{\"message\": \"sation n° "+stationNumber+"\" non implémenter\"}";
        return message;
    }

}
