package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.PersonsByAddress;
import com.safety.safetyNet.service.SafetyNetChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author o.froidefond
 */
@RestController
@CrossOrigin("http://localhost:3000/")
public class SafetyNetChildrenController {

    @Autowired
    SafetyNetChildrenService safetyNetChildrenService;

    /**
     * retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     *
     * @param address adresse rechercher
     * @return la liste des enfant trouver a l'adresse
     */
    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildByAddress(@RequestParam String address) {
        List<PersonsByAddress> children = safetyNetChildrenService.getChildrenByAddress(address);
        if (children.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(children);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(children);
        }
    }
}
