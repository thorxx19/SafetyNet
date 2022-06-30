package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.ResponsePersonsChildren;
import com.safety.safetyNet.service.SafetyNetChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author o.froidefond
 */
@RestController
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
    public ResponsePersonsChildren getAddressChild(@RequestParam String address) {
        ResponsePersonsChildren children = safetyNetChildrenService.getChildrenThisAddress(address);
        return children;
    }
}
