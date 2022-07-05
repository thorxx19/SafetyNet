package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.PersonInfo;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetRepository;
import com.safety.safetyNet.service.SafetyNetPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author o.froidefond
 */
@RestController
@Slf4j
public class SafetyNetPersonController {

    @Autowired
    SafetyNetPersonService safetyNetPersonService;
    @Autowired
    SafetyNetRepository safetyNetRepository;

    /**
     * retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant.
     *
     * @param firstName le prénom rechercher
     * @param lastName  le nom rechercher
     * @return la fiche de la personne compléte
     */
    @GetMapping("/personInfo")
    public List<PersonInfo> getMedicalRecordsOfThisPerson(@RequestParam String firstName, String lastName) {
        List<PersonInfo> persons = safetyNetPersonService.getPersonInfo(firstName, lastName);
        log.info("Requête reçue -> getMedicalRecordsOfThisPerson :Prénom:{},Nom:{}",firstName,lastName );
        log.info("Objet retourné -> getMedicalRecordsOfThisPerson :{}",persons);
        return persons;
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une nouvelle personne.
     *
     * @param newPerson un object de type Persons.
     */
    @PostMapping("/person")
    public void postNewPerson(@RequestBody Persons newPerson) {
        ListSafety listSafety = safetyNetPersonService.postNewPerson(newPerson);
        safetyNetRepository.writeData(listSafety);
        log.info("Requête reçue -> postNewPerson :{}",newPerson);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une personne en moins.
     *
     * @param deletePerson un object de type Persons.
     */
    @DeleteMapping("/person")
    public void deletePerson(@RequestBody DeletePerson deletePerson) {
        ListSafety listSafety = safetyNetPersonService.deletePerson(deletePerson);
        safetyNetRepository.writeData(listSafety);
        log.info("Requête reçue -> deletePerson :{}",deletePerson);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec une personne modifiée.
     *
     * @param putPerson un object de type Persons.
     */
    @PutMapping("/person")
    public void putPerson(@RequestBody Persons putPerson) {
        ListSafety listSafety = safetyNetPersonService.putPerson(putPerson);
        safetyNetRepository.writeData(listSafety);
        log.info("Requête reçue -> putPerson :{}",putPerson);

    }
}
