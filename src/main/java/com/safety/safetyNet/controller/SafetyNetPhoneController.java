package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.ResponsePhone;
import com.safety.safetyNet.service.SafetyNetPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public ResponsePhone getNumberPhone(@RequestParam int firestation) {
        ResponsePhone phone = safetyNetPhoneService.getNumberPhoneThisFireStation(firestation);
        return phone;
    }
}
