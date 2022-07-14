package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
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
public class SafetyNetPersonService {

    @Autowired
    SafetyNetRepository safetyNetRepository;
    @Autowired
    SafetyNetCalculatorAgeBirthdate safetyNetCalculatorAgeBirthdate;

    /**
     * fonction pour récupérer une personne avec les antécédant médicaux
     *
     * @param firstname le prénom
     * @param lastName  le nom de famille
     * @return une personne avec les infos
     */
    public List<PersonInfo> getPersonInfo(String firstname, String lastName) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<MedicalRecords> dataMedicalRecords = data.getMedicalrecords();

        PersonInfo personInfo = new PersonInfo();
        List<PersonInfo> personInfoList = new ArrayList<>();

        Persons personsStream = dataPersons.stream().filter(x -> firstname.equals(x.getFirstName())
                && lastName.equals(x.getLastName())).findAny().orElse(null);

        MedicalRecords medicalStream = dataMedicalRecords.stream().filter(x -> firstname.equals(x.getFirstName())
                && lastName.equals(x.getLastName())).findAny().orElse(null);


        if (personsStream != null && medicalStream != null) {

            long yearBirth = safetyNetCalculatorAgeBirthdate.calculeDateBirthdate(medicalStream);

            personInfo.setLastName(personsStream.getLastName());
            personInfo.setAddress(personsStream.getAddress());
            personInfo.setAge(yearBirth);
            personInfo.setEmail(personsStream.getEmail());
            personInfo.setMedications(medicalStream.getMedications());
            personInfo.setAllergies(medicalStream.getAllergies());
            personInfoList.add(personInfo);
        }

        return personInfoList;
    }

    /**
     * fonction ajout d'une personne
     *
     * @param newPerson object reçu
     * @return le nouvelle object de type listSafety
     */
    public ListSafety postNewPerson(Persons newPerson) {

        ListSafety listSafety = new ListSafety();
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        List<Persons> personsList = safetyNetRepository.getData().getPersons();
        boolean verifPerson = true;

        for (Persons person : personsList) {
            if (newPerson.getFirstName().equals(person.getFirstName()) && newPerson.getLastName().equals(person.getLastName())) {
                verifPerson = false;
                break;
            }
        }
        if (verifPerson) {
            personsList.add(newPerson);
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecords);

        return listSafety;
    }

    /**
     * fonction qui supprime une personne en fonction du nom et prénom
     *
     * @param deletePerson object de type DeletePerson
     * @return le nouvelle object de type listSafety
     */
    public ListSafety deletePerson(DeletePerson deletePerson) {

        List<Persons> personsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        safetyNetRepository.getData().getPersons().stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).forEach(x -> personsList.add(x));

        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecords);

        return listSafety;
    }

    /**
     * fonction pour modifier une personne
     *
     * @param putPerson l'object reçu
     * @return le nouvelle object de type listSafety
     */
    public ListSafety putPerson(Persons putPerson) {

        ListSafety listSafety = new ListSafety();
        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<Persons> personsList = safetyNetRepository.getData().getPersons();

        for (Persons person : personsList) {
            if (putPerson.getFirstName().equals(person.getFirstName()) && putPerson.getLastName().equals(person.getLastName())) {
                person.setAddress(putPerson.getAddress());
                person.setCity(putPerson.getCity());
                person.setZip(putPerson.getZip());
                person.setPhone(putPerson.getPhone());
                person.setEmail(putPerson.getEmail());
                break;
            }
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecords);

        return listSafety;
    }
}
