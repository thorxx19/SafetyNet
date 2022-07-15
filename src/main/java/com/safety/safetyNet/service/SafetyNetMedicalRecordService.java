package com.safety.safetyNet.service;

import com.safety.safetyNet.model.FireStations;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetFireStationRepository;
import com.safety.safetyNet.repository.SafetyNetMedicalRecordsRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;

/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetMedicalRecordService {
    @Autowired
    SafetyNetMedicalRecordsRepository safetyNetMedicalRecordsRepository;
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetFireStationRepository safetyNetFireStationRepository;




    /**
     * Fonction pour ajouter un dossier médical a l'objet listSafety.
     *
     * @param postMedicalRecord un objet de type MedicalRecords.
     * @return un object de type ListSafety.
     */
    public ListSafety postMedicalRecord(MedicalRecords postMedicalRecord) {
        ListSafety listSafety = new ListSafety();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);
        boolean verifMedicalRecord = true;

        for (MedicalRecords medicalRecord : dataMedical) {
            if (postMedicalRecord.getFirstName().equals(medicalRecord.getFirstName()) && postMedicalRecord.getLastName().equals(medicalRecord.getLastName())) {
                verifMedicalRecord = false;
                break;
            }
        }
        if (verifMedicalRecord) {
            dataMedical.add(postMedicalRecord);
        }

        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);

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
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);

        for (MedicalRecords medicalRecord : dataMedical) {
            if (putMedicalRecord.getFirstName().equals(medicalRecord.getFirstName()) && putMedicalRecord.getLastName().equals(medicalRecord.getLastName())) {
                medicalRecord.setBirthdate(putMedicalRecord.getBirthdate());
                medicalRecord.setMedications(putMedicalRecord.getMedications());
                medicalRecord.setAllergies(putMedicalRecord.getAllergies());
                break;
            }
        }

        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);

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
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);
        dataMedical.stream().filter(x -> !deleteMedicalrecord.getFirstName().equals(x.getFirstName())
                && !deleteMedicalrecord.getLastName().equals(x.getLastName())).forEach(x -> dataMedical.add(x));


        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);

        return listSafety;
    }
}
