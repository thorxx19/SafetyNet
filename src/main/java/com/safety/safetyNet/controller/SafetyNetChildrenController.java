package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.PersonsByAddress;
import com.safety.safetyNet.service.SafetyNetChildrenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author o.froidefond
 */
@RestController
@Slf4j
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
    public List<PersonsByAddress> getChildByAddress(@RequestParam String address) {
        List<PersonsByAddress> children = safetyNetChildrenService.getChildrenByAddress(address);
        log.info("Requête reçue -> getAddressChild :{}", address);
        log.info("Objet retourné -> getAddressChild :{}", children);
        return children;
    }
}
