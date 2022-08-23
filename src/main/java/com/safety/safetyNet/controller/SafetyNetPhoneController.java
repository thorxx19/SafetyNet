package com.safety.safetyNet.controller;


import com.safety.safetyNet.service.SafetyNetPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeSet;

/**
 * @author o.froidefond
 */
@RestController
@CrossOrigin("http://localhost:3000/")
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
    public ResponseEntity<?> getNumberByPhone(@RequestParam int firestation) {
        TreeSet<String> phone = safetyNetPhoneService.getNumberPhoneByFireStation(firestation);
        if (phone.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(phone);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(phone);
        }
    }
}
