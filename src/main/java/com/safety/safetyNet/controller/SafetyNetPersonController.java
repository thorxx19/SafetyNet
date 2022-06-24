package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.NewPerson;
import com.safety.safetyNet.model.ResponsePersonInfo;
import com.safety.safetyNet.repository.SafetyNetRepository;
import com.safety.safetyNet.service.SafetyNetPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public ResponsePersonInfo getMedicalRecordsOfThisPerson(@RequestParam String firstName, String lastName) {
        ResponsePersonInfo persons = safetyNetPersonService.getPersonInfo(firstName, lastName);
        return persons;
    }
    @PostMapping("/person")
    public void postNewPerson(@RequestBody NewPerson newPerson) {
        ListSafety listSafety = safetyNetPersonService.postNewPerson(newPerson);
        safetyNetRepository.writeData(listSafety);
    }
    @DeleteMapping("/person")
    public void deletePerson(@RequestBody DeletePerson deletePerson){
        ListSafety listSafety = safetyNetPersonService.deletePerson(deletePerson);
        safetyNetRepository.writeData(listSafety);
    }
    @PutMapping("/person")
    public void putPerson(@RequestBody NewPerson putPerson){
        ListSafety listSafety = safetyNetPersonService.putPerson(putPerson);
        safetyNetRepository.writeData(listSafety);
    }
}
