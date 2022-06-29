package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SafetyNetPersonService {

    @Autowired
    SafetyNetRepository safetyNetRepository;

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

        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy");

        if (personsStream != null && medicalStream != null) {
            try {
                Date birth = dateTimeFormatter.parse(medicalStream.getBirthdate());
                Date todayParse = today.getTime();
                long result = todayParse.getTime() - birth.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long resultDay = time.convert(result, TimeUnit.MILLISECONDS);
                long yearBirth = resultDay / 365;

                personInfo.setLastName(personsStream.getLastName());
                personInfo.setAddress(personsStream.getAddress());
                personInfo.setAge(yearBirth);
                personInfo.setEmail(personsStream.getEmail());
                personInfo.setMedications(medicalStream.getMedications());
                personInfo.setAllergies(medicalStream.getAllergies());
                personInfoList.add(personInfo);
            } catch (Exception e) {
                log.error("error :", e);
            }
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

        ArrayList<Persons> personsList = new ArrayList<>();

        ListSafety listSafety = new ListSafety();
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());
        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        Persons persons = new Persons();

        safetyNetRepository.getData().getPersons().stream().filter(x -> !newPerson.getFirstName().equals(x.getFirstName())
                && !newPerson.getFirstName().equals(x.getLastName())).forEach(x -> personsList.add(x));

        persons.setFirstName(newPerson.getFirstName());
        persons.setLastName(newPerson.getLastName());
        persons.setAddress(newPerson.getAddress());
        persons.setCity(newPerson.getCity());
        persons.setZip(newPerson.getZip());
        persons.setPhone(newPerson.getPhone());
        persons.setEmail(newPerson.getEmail());
        personsList.add(persons);

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
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        safetyNetRepository.getData().getPersons().stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).forEach(x -> personsList.add(x));

        safetyNetRepository.getData().getMedicalrecords().stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).forEach(x -> medicalRecordsList.add(x));

        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);

        return listSafety;
    }

    /**
     * fonction pour modifier une personne
     *
     * @param putPerson l'object reçu
     * @return le nouvelle object de type listSafety
     */
    public ListSafety putPerson(Persons putPerson) {

        List<Persons> personsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        safetyNetRepository.getData().getPersons().stream().filter(x -> !putPerson.getFirstName().equals(x.getFirstName())
                && !putPerson.getFirstName().equals(x.getLastName())).forEach(x -> personsList.add(x));

        Persons personPut = new Persons();

        personPut.setFirstName(putPerson.getFirstName());
        personPut.setLastName(putPerson.getLastName());
        personPut.setAddress(putPerson.getAddress());
        personPut.setCity(putPerson.getCity());
        personPut.setZip(putPerson.getZip());
        personPut.setPhone(putPerson.getPhone());
        personPut.setEmail(putPerson.getEmail());

        personsList.add(personPut);

        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecords);

        return listSafety;
    }
}
