package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetMedicalRecordService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author o.froidefond
 */

@RestController
@CrossOrigin("http://localhost:3000/")
public class SafetyNetMedicalRecordController {
    @Autowired
    SafetyNetMedicalRecordService safetyNetMedicalRecordService;
    @Autowired
    SafetyNetWriteFileRepository safetyNetWriteFileRepository;

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec un nouveaux dossier médical.
     *
     * @param postMedicalRecord un object de type MedicalRecords.
     * @return http status
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<?> postMedicalRecord(@Valid @RequestBody MedicalRecords postMedicalRecord) {
        ListSafety listSafety = safetyNetMedicalRecordService.postMedicalRecord(postMedicalRecord);
        safetyNetWriteFileRepository.writeData(listSafety);
        return ResponseEntity.status(HttpStatus.CREATED).body(Strings.EMPTY);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec un dossier médical modifié
     *
     * @param putMedicalRecord un object de type MedicalRecords.
     */
    @PutMapping("/medicalRecord")
    public void putMedicalRecord(@Valid @RequestBody MedicalRecords putMedicalRecord) {
        ListSafety listSafety = safetyNetMedicalRecordService.putMedicalRecord(putMedicalRecord);
        safetyNetWriteFileRepository.writeData(listSafety);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec un dossier médical en moins.
     *
     * @param deleteMedicalRecord un object de type MedicalRecords.
     */
    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@Valid @RequestBody DeletePerson deleteMedicalRecord) {
        ListSafety listSafety = safetyNetMedicalRecordService.deleteMedicalRecord(deleteMedicalRecord);
        safetyNetWriteFileRepository.writeData(listSafety);
    }
}
