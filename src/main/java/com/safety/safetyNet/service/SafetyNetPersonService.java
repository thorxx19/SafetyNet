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
    public ResponsePersonInfo getPersonInfo(String firstname, String lastName) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<MedicalRecords> dataMedicalRecords = data.getMedicalrecords();

        PersonInfo personInfo = new PersonInfo();
        ResponsePersonInfo responsePersonInfo = new ResponsePersonInfo();
        for (Persons person : dataPersons) {
            if (firstname.equals(person.getFirstName()) && lastName.equals(person.getLastName())) {
                for (MedicalRecords medic : dataMedicalRecords) {
                    if (person.getLastName().equals(medic.getLastName()) && person.getFirstName()
                            .equals(medic.getFirstName())) {
                        Calendar today = Calendar.getInstance();
                        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date birth = dateTimeFormatter.parse(medic.getBirthdate());
                            Date todayParse = today.getTime();
                            long result = todayParse.getTime() - birth.getTime();
                            TimeUnit time = TimeUnit.DAYS;
                            long resultDay = time.convert(result, TimeUnit.MILLISECONDS);
                            long yearBirth = resultDay / 365;

                            personInfo.setLastName(person.getLastName());
                            personInfo.setAddress(person.getAddress());
                            personInfo.setAge(yearBirth);
                            personInfo.setEmail(person.getEmail());
                            personInfo.setMedications(medic.getMedications());
                            personInfo.setAllergies(medic.getAllergies());

                        } catch (Exception e) {
                            log.error("error :", e);
                        }
                    }
                }
            }
        }
        responsePersonInfo.setPersonInfo(personInfo);
        return responsePersonInfo;
    }

    public ListSafety postNewPerson(NewPerson newPerson) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<MedicalRecords> dataMedicalRecords = data.getMedicalrecords();
        List<FireStations> dataFireStation = data.getFirestations();

        boolean verifPerson = false;
        boolean verifMedical = false;

        ArrayList<Persons> personsList = new ArrayList<>();
        ArrayList<FireStations> fireStationsList = new ArrayList<>();
        ArrayList<MedicalRecords> medicalRecordsList = new ArrayList<>();

        ListSafety listSafety = new ListSafety();
        for (Persons person : dataPersons) {
            if (!newPerson.getFirstName().equals(person.getFirstName()) && !newPerson.getLastName().equals(person.getLastName())) {

                Persons persons = new Persons();

                persons.setFirstName(person.getFirstName());
                persons.setLastName(person.getLastName());
                persons.setAddress(person.getAddress());
                persons.setCity(person.getCity());
                persons.setZip(person.getZip());
                persons.setPhone(person.getPhone());
                persons.setEmail(person.getEmail());
                personsList.add(persons);

            } else {
                Persons persons = new Persons();

                persons.setFirstName(person.getFirstName());
                persons.setLastName(person.getLastName());
                persons.setAddress(person.getAddress());
                persons.setCity(person.getCity());
                persons.setZip(person.getZip());
                persons.setPhone(person.getPhone());
                persons.setEmail(person.getEmail());
                personsList.add(persons);
                verifPerson = true;
            }
        }
        for (MedicalRecords medical : dataMedicalRecords) {
            if (!newPerson.getFirstName().equals(medical.getFirstName()) && !newPerson.getLastName().equals(medical.getLastName())) {
                MedicalRecords medicalRecords = new MedicalRecords();

                medicalRecords.setFirstName(medical.getFirstName());
                medicalRecords.setLastName(medical.getLastName());
                medicalRecords.setBirthdate(medical.getBirthdate());
                medicalRecords.setMedications(medical.getMedications());
                medicalRecords.setAllergies(medical.getAllergies());
                medicalRecordsList.add(medicalRecords);
            } else {
                MedicalRecords medicalRecords = new MedicalRecords();

                medicalRecords.setFirstName(medical.getFirstName());
                medicalRecords.setLastName(medical.getLastName());
                medicalRecords.setBirthdate(medical.getBirthdate());
                medicalRecords.setMedications(medical.getMedications());
                medicalRecords.setAllergies(medical.getAllergies());
                medicalRecordsList.add(medicalRecords);

                verifMedical = true;
            }
        }
        for (FireStations fireStation : dataFireStation) {
            FireStations firestations = new FireStations();

            firestations.setAddress(fireStation.getAddress());
            firestations.setStation(fireStation.getStation());
            fireStationsList.add(firestations);
        }
        if (!verifPerson || !verifMedical) {
            List<String> medicationsList = new ArrayList<>();
            List<String> allergiesList = new ArrayList<>();
            //todo faire en sorte de gérer plusieur string

            if (newPerson.getMedications() != null) {
                medicationsList.add(newPerson.getMedications());
            }
            if (newPerson.getAllergies() != null) {
                allergiesList.add(newPerson.getAllergies());
            }
            Persons persons = new Persons();
            MedicalRecords medicalRecords = new MedicalRecords();

            persons.setFirstName(newPerson.getFirstName());
            persons.setLastName(newPerson.getLastName());
            persons.setAddress(newPerson.getAddress());
            persons.setCity(newPerson.getCity());
            persons.setZip(newPerson.getZip());
            persons.setPhone(newPerson.getPhone());
            persons.setEmail(newPerson.getEmail());
            personsList.add(persons);

            medicalRecords.setFirstName(newPerson.getFirstName());
            medicalRecords.setLastName(newPerson.getLastName());
            medicalRecords.setBirthdate(newPerson.getBirthdate());
            medicalRecords.setMedications(medicationsList);
            medicalRecords.setAllergies(allergiesList);
            medicalRecordsList.add(medicalRecords);
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);


        return listSafety;
    }
    public ListSafety deletePerson(DeletePerson deletePerson){
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<MedicalRecords> dataMedicalRecords = data.getMedicalrecords();
        List<FireStations> dataFireStation = data.getFirestations();


        ArrayList<Persons> personsList = new ArrayList<>();
        ArrayList<FireStations> fireStationsList = new ArrayList<>();
        ArrayList<MedicalRecords> medicalRecordsList = new ArrayList<>();

        ListSafety listSafety = new ListSafety();
        for (Persons person : dataPersons) {
            if (!deletePerson.getFirstName().equals(person.getFirstName()) && !deletePerson.getLastName().equals(person.getLastName())) {

                Persons persons = new Persons();

                persons.setFirstName(person.getFirstName());
                persons.setLastName(person.getLastName());
                persons.setAddress(person.getAddress());
                persons.setCity(person.getCity());
                persons.setZip(person.getZip());
                persons.setPhone(person.getPhone());
                persons.setEmail(person.getEmail());
                personsList.add(persons);

            }
        }
        for (MedicalRecords medical : dataMedicalRecords) {
            if (!deletePerson.getFirstName().equals(medical.getFirstName()) && !deletePerson.getLastName().equals(medical.getLastName())) {
                MedicalRecords medicalRecords = new MedicalRecords();

                medicalRecords.setFirstName(medical.getFirstName());
                medicalRecords.setLastName(medical.getLastName());
                medicalRecords.setBirthdate(medical.getBirthdate());
                medicalRecords.setMedications(medical.getMedications());
                medicalRecords.setAllergies(medical.getAllergies());
                medicalRecordsList.add(medicalRecords);
            }
        }
        for (FireStations fireStation : dataFireStation) {
            FireStations firestations = new FireStations();

            firestations.setAddress(fireStation.getAddress());
            firestations.setStation(fireStation.getStation());
            fireStationsList.add(firestations);
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);


        return listSafety;
    }
}
