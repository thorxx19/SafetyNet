package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.repository.SafetyNetRepository;
import com.safety.safetyNet.service.SafetyNetMedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SafetyNetMedicalRecordController {
    @Autowired
    SafetyNetMedicalRecordService safetyNetMedicalRecordService;
    @Autowired
    SafetyNetRepository safetyNetRepository;

    @PostMapping("/medicalRecord")
    public void postMedicalRecord(@RequestBody MedicalRecords postMedicalRecord){
        ListSafety listSafety = safetyNetMedicalRecordService.postMedicalRecord(postMedicalRecord);
        safetyNetRepository.writeData(listSafety);
    }
    @PutMapping("/medicalRecord")
    public void putMedicalRecord(@RequestBody MedicalRecords putMedicalRecord){
        ListSafety listSafety = safetyNetMedicalRecordService.putMedicalRecord(putMedicalRecord);
        safetyNetRepository.writeData(listSafety);
    }
}
