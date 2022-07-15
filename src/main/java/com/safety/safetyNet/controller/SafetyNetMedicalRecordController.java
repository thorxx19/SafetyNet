package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetMedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.safety.safetyNet.constantes.SafetyNetConstantes.*;

/**
 * @author o.froidefond
 */

@RestController
@Slf4j
public class SafetyNetMedicalRecordController {
    @Autowired
    SafetyNetMedicalRecordService safetyNetMedicalRecordService;
    @Autowired
    SafetyNetWriteFileRepository safetyNetWriteFileRepository;

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec un nouveaux dossier médical.
     *
     * @param postMedicalRecord un object de type MedicalRecords.
     */
    @PostMapping("/medicalRecord")
    public void postMedicalRecord(@RequestBody MedicalRecords postMedicalRecord) {
        ListSafety listSafety = safetyNetMedicalRecordService.postMedicalRecord(postMedicalRecord);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête POST postMedicalRecord : {}", postMedicalRecord);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec un dossier médical modifié
     *
     * @param putMedicalRecord un object de type MedicalRecords.
     */
    @PutMapping("/medicalRecord")
    public void putMedicalRecord(@RequestBody MedicalRecords putMedicalRecord) {
        ListSafety listSafety = safetyNetMedicalRecordService.putMedicalRecord(putMedicalRecord);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête PUT putMedicalRecord : {}", putMedicalRecord);
    }

    /**
     * Retourne une listSafety modifiée vers "safetyNetRepository.writeData()" avec un dossier médical en moins.
     *
     * @param deleteMedicalRecord un object de type MedicalRecords.
     */
    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestBody MedicalRecords deleteMedicalRecord) {
        ListSafety listSafety = safetyNetMedicalRecordService.deleteMedicalRecord(deleteMedicalRecord);
        safetyNetWriteFileRepository.writeData(listSafety);
        log.info("Requête DELETE deleteMedicalRecord : {}", deleteMedicalRecord);
    }
}
