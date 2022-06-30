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

/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetMedicalRecordService {
    @Autowired
    SafetyNetRepository safetyNetRepository;

    /**
     * Fonction pour ajouter un dossier médical a l'objet listSafety.
     *
     * @param postMedicalRecord un objet de type MedicalRecords.
     * @return un object de type ListSafety.
     */
    public ListSafety postMedicalRecord(MedicalRecords postMedicalRecord) {
        ListSafety listSafety = new ListSafety();
        List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<MedicalRecords> medicalRecordsList = safetyNetRepository.getData().getMedicalrecords();
        boolean verifMedicalRecord = true;

        for (MedicalRecords medicalRecord : medicalRecordsList) {
            if (postMedicalRecord.getFirstName().equals(medicalRecord.getFirstName()) && postMedicalRecord.getLastName().equals(medicalRecord.getLastName())) {
                verifMedicalRecord = false;
                break;
            }
        }
        if (verifMedicalRecord) {
            medicalRecordsList.add(postMedicalRecord);
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);

        return listSafety;
    }

    /**
     * Fonction pour modifier un dossier médical dans l'objet listSafety.
     *
     * @param putMedicalRecord un objet de type MedicalRecords.
     * @return un objet de type ListSafety.
     */
    public ListSafety putMedicalRecord(MedicalRecords putMedicalRecord) {
        ListSafety listSafety = new ListSafety();
        List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<MedicalRecords> medicalRecordsList = safetyNetRepository.getData().getMedicalrecords();

        for (MedicalRecords medicalRecord : medicalRecordsList) {
            if (putMedicalRecord.getFirstName().equals(medicalRecord.getFirstName()) && putMedicalRecord.getLastName().equals(medicalRecord.getLastName())) {
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

    /**
     * Fonction pour supprimer un dossier médical dans l'objet listSafety.
     *
     * @param deleteMedicalrecord un objet de type MedicalRecord.
     * @return un objet de type ListSafety.
     */
    public ListSafety deleteMedicalRecord(MedicalRecords deleteMedicalrecord) {
        ListSafety listSafety = new ListSafety();
        List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();

        safetyNetRepository.getData().getMedicalrecords().stream().filter(x -> !deleteMedicalrecord.getFirstName().equals(x.getFirstName())
                && !deleteMedicalrecord.getLastName().equals(x.getLastName())).forEach(x -> medicalRecordsList.add(x));


        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);

        return listSafety;
    }
}
