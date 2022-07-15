package com.safety.safetyNet.repository;

import com.safety.safetyNet.model.MedicalRecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Slf4j
public class SafetyNetMedicalRecordsRepository {

    @Autowired
    SafetyNetReadFileRepository safetyNetReadFileRepository;

    public List<MedicalRecords> getMedicalRecords(String file){

    return safetyNetReadFileRepository.readfile(file).getMedicalrecords();

    }

}
