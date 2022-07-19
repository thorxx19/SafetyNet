package com.safety.safetyNet.controller;


import com.safety.safetyNet.service.SafetyNetPhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeSet;

/**
 * @author o.froidefond
 */
@RestController
@Slf4j
public class SafetyNetPhoneController {
    @Autowired
    SafetyNetPhoneService safetyNetPhoneService;

    /**
     * retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers.
     *
     * @param firestation n° de la station
     * @return liste de n° de tel des résident
     */
    @GetMapping("/phoneAlert")
    public TreeSet<String> getNumberByPhone(@RequestParam int firestation) {
        TreeSet<String> phone = safetyNetPhoneService.getNumberPhoneByFireStation(firestation);
        log.info("Requête reçue -> getNumberPhone :{}", firestation);
        log.info("Objet retourné -> getNumberPhone :{}", phone);
        return phone;
    }
}
