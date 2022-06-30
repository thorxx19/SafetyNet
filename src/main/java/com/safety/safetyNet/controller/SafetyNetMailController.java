package com.safety.safetyNet.controller;


import com.safety.safetyNet.service.SafetyNetMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeSet;

/**
 * @author o.froidefond
 */
@RestController
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
    public TreeSet<String> getMailAllPersons(@RequestParam String city) {
        TreeSet<String> listEmail = safetyNetMailService.getAllMail(city);
        return listEmail;
    }
}
