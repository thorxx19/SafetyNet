package com.safety.safetyNet.service;

import com.safety.safetyNet.model.FireStations;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SafetyNetMedicalRecordService {
    @Autowired
    SafetyNetRepository safetyNetRepository;

    public ListSafety postMedicalRecord(MedicalRecords postMedicalRecord){
        ListSafety listSafety = new ListSafety();
        List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<MedicalRecords> medicalRecordsList = safetyNetRepository.getData().getMedicalrecords();
        boolean verifMedicalRecord = true;

        for (MedicalRecords medicalRecord: medicalRecordsList){
            if (postMedicalRecord.getFirstName().equals(medicalRecord.getFirstName()) && postMedicalRecord.getLastName().equals(medicalRecord.getLastName())){
                verifMedicalRecord = false;
                break;
            }
        }
        if (verifMedicalRecord){
            medicalRecordsList.add(postMedicalRecord);
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);

        return listSafety;
    }
public ListSafety putMedicalRecord(MedicalRecords putMedicalRecord){
    ListSafety listSafety = new ListSafety();
    List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
    List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
    List<MedicalRecords> medicalRecordsList = safetyNetRepository.getData().getMedicalrecords();

    for (MedicalRecords medicalRecord:medicalRecordsList) {
        if (putMedicalRecord.getFirstName().equals(medicalRecord.getFirstName()) && putMedicalRecord.getLastName().equals(medicalRecord.getLastName())){
            medicalRecord.setBirthdate(putMedicalRecord.getBirthdate());
            medicalRecord.setMedications(putMedicalRecord.getMedications());
            medicalRecord.setAllergies(putMedicalRecord.getAllergies());
            break;
        }
    }

    listSafety.setPersons(personsList);
    listSafety.setFirestations(fireStationsList);
    listSafety.setMedicalrecords(medicalRecordsList);

    return listSafety;
}
}
