package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.PersonInfo;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetPersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * @author o.froidefond
 */
@RestController
@Slf4j
public class SafetyNetPersonController {

    @Autowired
    SafetyNetPersonService safetyNetPersonService;
    @Autowired
    SafetyNetWriteFileRepository safetyNetWriteFileRepository;

    /**
     * retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant.
     *
     * @param firstName le prénom rechercher
     * @param lastName  le nom rechercher
     * @return la fiche de la personne compléte
     */
    @GetMapping("/personInfo")
    public ResponseEntity<?> getPatientCardByName(@RequestParam String firstName, String lastName) {
        List<PersonInfo> persons = safetyNetPersonService.getPersonCardInfoByName(firstName, lastName);
        log.info("Requête reçue -> getMedicalRecordsOfThisPerson :Prénom:{},Nom:{}", firstName, lastName);
        log.info("Objet retourné -> getMedicalRecordsOfThisPerson :{}", persons);
        if (persons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(persons);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(persons);
        }
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une nouvelle personne.
     *
     * @param newPerson un object de type Persons.
     * @return HTTP status
     */
    @PostMapping("/person")
    public ResponseEntity<?> postNewPerson(@Valid @RequestBody Persons newPerson) {
        ListSafety listSafety = safetyNetPersonService.postNewPerson(newPerson);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête reçue -> postNewPerson :{}", newPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(Strings.EMPTY);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une personne en moins.
     *
     * @param deletePerson un object de type Persons.
     */
    @DeleteMapping("/person")
    public void deletePerson(@Valid @RequestBody DeletePerson deletePerson) {
        ListSafety listSafety = safetyNetPersonService.deletePerson(deletePerson);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête reçue -> deletePerson :{}", deletePerson);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une personne modifiée.
     *
     * @param putPerson un object de type Persons.
     */
    @PutMapping("/person")
    public void putPerson(@Valid @RequestBody Persons putPerson) {
        ListSafety listSafety = safetyNetPersonService.putPerson(putPerson);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête reçue -> putPerson :{}", putPerson);

    }
}
