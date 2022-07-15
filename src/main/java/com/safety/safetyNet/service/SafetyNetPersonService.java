package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetFireStationRepository;
import com.safety.safetyNet.repository.SafetyNetMedicalRecordsRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;

/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetPersonService {


    @Autowired
    SafetyNetCalculatorAgeBirthdate safetyNetCalculatorAgeBirthdate;
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetFireStationRepository safetyNetFireStationRepository;
    @Autowired
    SafetyNetMedicalRecordsRepository safetyNetMedicalRecordsRepository;





    /**
     * fonction pour récupérer une personne avec les antécédant médicaux
     *
     * @param firstname le prénom
     * @param lastName  le nom de famille
     * @return une personne avec les infos
     */
    public List<PersonInfo> getPersonInfo(String firstname, String lastName) {
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);



        PersonInfo personInfo = new PersonInfo();
        List<PersonInfo> personInfoList = new ArrayList<>();

        Persons personsStream = dataPersons.stream().filter(x -> firstname.equals(x.getFirstName())
                && lastName.equals(x.getLastName())).findAny().orElse(null);

        MedicalRecords medicalStream = dataMedical.stream().filter(x -> firstname.equals(x.getFirstName())
                && lastName.equals(x.getLastName())).findAny().orElse(null);


        if (personsStream != null && medicalStream != null) {

            long yearBirth = safetyNetCalculatorAgeBirthdate.calculeDateBirthdate(medicalStream.getBirthdate());

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
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);
        ListSafety listSafety = new ListSafety();

        boolean verifPerson = true;

        for (Persons person : dataPersons) {
            if (newPerson.getFirstName().equals(person.getFirstName()) && newPerson.getLastName().equals(person.getLastName())) {
                verifPerson = false;
                break;
            }
        }
        if (verifPerson) {
            dataPersons.add(newPerson);
        }

        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);

        return listSafety;
    }

    /**
     * fonction qui supprime une personne en fonction du nom et prénom
     *
     * @param deletePerson object de type DeletePerson
     * @return le nouvelle object de type listSafety
     */
    public ListSafety deletePerson(DeletePerson deletePerson) {
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);
        List<Persons> personsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        dataPersons.stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).forEach(x -> personsList.add(x));


        listSafety.setPersons(personsList);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);

        return listSafety;
    }

    /**
     * fonction pour modifier une personne
     *
     * @param putPerson l'object reçu
     * @return le nouvelle object de type listSafety
     */
    public ListSafety putPerson(Persons putPerson) {
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(PATH_FILE);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);
        ListSafety listSafety = new ListSafety();

        for (Persons person : dataPersons) {
            if (putPerson.getFirstName().equals(person.getFirstName()) && putPerson.getLastName().equals(person.getLastName())) {
                person.setAddress(putPerson.getAddress());
                person.setCity(putPerson.getCity());
                person.setZip(putPerson.getZip());
                person.setPhone(putPerson.getPhone());
                person.setEmail(putPerson.getEmail());
                break;
            }
        }

        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);

        return listSafety;
    }
}
