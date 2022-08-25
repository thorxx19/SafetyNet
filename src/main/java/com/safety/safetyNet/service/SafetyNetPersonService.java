package com.safety.safetyNet.service;

import com.safety.safetyNet.configuration.SafetyNetConfiguration;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.PersonInfo;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.model.FireStations;
import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.repository.SafetyNetFireStationRepository;
import com.safety.safetyNet.repository.SafetyNetMedicalRecordsRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetPersonService {

    @Autowired
    SafetyNetCalculatorAgeBirthdate safetyNetCalculatorAgeBirthdate;
    @Autowired
    SafetyNetFireStationRepository safetyNetFireStationRepository;
    @Autowired
    SafetyNetMedicalRecordsRepository safetyNetMedicalRecordsRepository;
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetConfiguration safetyNetConfiguration;




    /**
     * fonction pour récupérer une liste de personnes avec les antécédant médicaux.
     *
     * @param firstname le prénom
     * @param lastName  le nom de famille
     * @return une liste de personnes qui ont le même nom de famille.
     */
    public List<PersonInfo> getPersonCardInfoByName(String firstname, String lastName) {
        log.debug("Start getPersonCardInfoByName");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);

        List<PersonInfo> personInfoList = new ArrayList<>();

        List<Persons> personsStream = dataPersons.stream().filter(x -> lastName.equals(x.getLastName()))
                .collect(Collectors.toList());

        List<MedicalRecords> medicalStream = dataMedical.stream().filter(x -> lastName.equals(x.getLastName()))
                .collect(Collectors.toList());


        for (int i = 0; i <= personsStream.size() - 1; i++) {
            PersonInfo personInfo = new PersonInfo();
            long yearBirth = safetyNetCalculatorAgeBirthdate.calculeDateBirthdate(medicalStream.get(i)
                    .getBirthdate());

            personInfo.setLastName(personsStream.get(i).getLastName());
            personInfo.setAddress(personsStream.get(i).getAddress());
            personInfo.setAge(yearBirth);
            personInfo.setEmail(personsStream.get(i).getEmail());
            personInfo.setMedications(medicalStream.get(i).getMedications());
            personInfo.setAllergies(medicalStream.get(i).getAllergies());
            personInfoList.add(personInfo);
        }
        log.debug("End getPersonCardInfoByName");
        return personInfoList;
    }

    /**
     * fonction ajout d'une personne
     *
     * @param newPerson object reçu
     * @return le nouvelle object de type listSafety
     */
    public ListSafety postNewPerson(Persons newPerson) {
        log.debug("Start postNewPerson");
        String pathFile = safetyNetConfiguration.getPathFile();
        ListSafety listSafety = new ListSafety();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        boolean verifPerson = true;

        for (Persons person : dataPersons) {
            if (newPerson.getFirstName().equals(person.getFirstName()) && newPerson.getLastName()
                    .equals(person.getLastName())) {
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
        log.debug("End postNewPerson");
        return listSafety;
    }

    /**
     * fonction qui supprime une personne en fonction du nom et prénom
     *
     * @param deletePerson object de type DeletePerson
     * @return le nouvelle object de type listSafety
     */
    public ListSafety deletePerson(DeletePerson deletePerson) {
        log.debug("Start deletePerson");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        List<Persons> personsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        dataPersons.stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).forEach(x -> personsList.add(x));


        listSafety.setPersons(personsList);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);
        log.debug("End deletePerson");
        return listSafety;
    }

    /**
     * fonction pour modifier une personne
     *
     * @param putPerson l'object reçu
     * @return le nouvelle object de type listSafety
     */
    public ListSafety putPerson(Persons putPerson) {
        log.debug("Start putPerson");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        ListSafety listSafety = new ListSafety();

        for (Persons person : dataPersons) {
            if (putPerson.getFirstName().equals(person.getFirstName()) && putPerson.getLastName()
                    .equals(person.getLastName())) {
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
        log.debug("End putPerson");
        return listSafety;
    }
}
