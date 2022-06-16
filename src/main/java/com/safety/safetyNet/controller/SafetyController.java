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

    /**
     * retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * @param address adresse rechercher
     * @return la liste des enfant trouver a l'adresse
     */
    @GetMapping("/childAlert")
    public String getAddressChild(@RequestParam String address){
        String message = "{\"message\": \"l'adresse de cette enfant et pas trouver\"}";
        return message;
    }

    /**
     *  retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers.
     * @param firestation n° de la station
     * @return liste de n° de tel des résident
     */
    @GetMapping("/phoneAlert")
    public String getNumberPhone(@RequestParam int firestation){
        String message = "{\"message\": \"pas de N° de tel a cette station\"}";
        return message;
    }

}