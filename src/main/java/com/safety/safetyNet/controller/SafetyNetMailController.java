package com.safety.safetyNet.controller;


import com.safety.safetyNet.service.SafetyNetMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeSet;

/**
 * @author o.froidefond
 */
@RestController
@Slf4j
public class SafetyNetMailController {

    @Autowired
    SafetyNetMailService safetyNetMailService;

    /**
     * retourner les adresses mail de tous les habitants de la ville.
     *
     * @param city nom de la ville
     * @return les mail des habitants
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<?> getAllMailByCity(@RequestParam String city) {
        TreeSet<String> listEmail = safetyNetMailService.getMailByCity(city);
        log.info("Requête reçue -> getMailAllPersons :{}", city);
        log.info("Objet retourné -> getMailAllPersons :{}", listEmail);
        if (listEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listEmail);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(listEmail);
        }
    }
}
