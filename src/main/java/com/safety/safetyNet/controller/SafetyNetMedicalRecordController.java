package com.safety.safetyNet.controller;

import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetMedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

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
     * @return http status
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<Object> postMedicalRecord(@Valid @RequestBody MedicalRecords postMedicalRecord) {
        ListSafety listSafety = safetyNetMedicalRecordService.postMedicalRecord(postMedicalRecord);
        safetyNetWriteFileRepository.writeData(listSafety);
        if (Objects.isNull(listSafety)){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(listSafety.getPersons())
                .toUri();
        log.info("Requête POST postMedicalRecord : {}", postMedicalRecord);
        return ResponseEntity.created(location).build();
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
        log.info("Requête PUT putMedicalRecord : {}", putMedicalRecord);
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
        log.info("Requête DELETE deleteMedicalRecord : {}", deleteMedicalRecord);
    }
}
