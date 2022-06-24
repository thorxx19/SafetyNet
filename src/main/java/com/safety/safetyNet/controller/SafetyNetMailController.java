package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.ResponseEmail;
import com.safety.safetyNet.service.SafetyNetMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEmail getMailAllPersons(@RequestParam String city) {
        ResponseEmail listEmail = safetyNetMailService.getAllMail(city);
        return listEmail;
    }
}
