package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            if (!newPerson.getPerson().getFirstName().equals(person.getFirstName()) && !newPerson.getPerson().getLastName().equals(person.getLastName())) {

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
            if (!newPerson.getMedical().getFirstName().equals(medical.getFirstName()) && !newPerson.getMedical().getLastName().equals(medical.getLastName())) {
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



            Persons persons = new Persons();
            MedicalRecords medicalRecords = new MedicalRecords();

            persons.setFirstName(newPerson.getPerson().getFirstName());
            persons.setLastName(newPerson.getPerson().getLastName());
            persons.setAddress(newPerson.getPerson().getAddress());
            persons.setCity(newPerson.getPerson().getCity());
            persons.setZip(newPerson.getPerson().getZip());
            persons.setPhone(newPerson.getPerson().getPhone());
            persons.setEmail(newPerson.getPerson().getEmail());
            personsList.add(persons);

            medicalRecords.setFirstName(newPerson.getMedical().getFirstName());
            medicalRecords.setLastName(newPerson.getMedical().getLastName());
            medicalRecords.setBirthdate(newPerson.getMedical().getBirthdate());
            medicalRecords.setMedications(newPerson.getMedical().getMedications());
            medicalRecords.setAllergies(newPerson.getMedical().getAllergies());
            medicalRecordsList.add(medicalRecords);
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);


        return listSafety;
    }

    /**
     * fonction qui supprime une personne en fonction du nom et prénom
     * @param deletePerson object de type DeletePerson
     * @return le nouvelle object de type listSafety
     */
    public ListSafety deletePerson(DeletePerson deletePerson) {

        List<Persons> personsList = new ArrayList<>();
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        safetyNetRepository.getData().getPersons().stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).forEach(personsList::add);

        safetyNetRepository.getData().getMedicalrecords().stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).forEach(medicalRecordsList::add);

        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);

        return listSafety;
    }

    public ListSafety putPerson(NewPerson putPerson) {

        List<Persons> personsList = new ArrayList<>();
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        safetyNetRepository.getData().getPersons().stream().filter(x -> !putPerson.getPerson().getFirstName().equals(x.getFirstName())
        && !putPerson.getPerson().getFirstName().equals(x.getLastName())).forEach(personsList::add);

                Persons personPut = new Persons();

                personPut.setFirstName(putPerson.getPerson().getFirstName());
                personPut.setLastName(putPerson.getPerson().getLastName());
                personPut.setAddress(putPerson.getPerson().getAddress());
                personPut.setCity(putPerson.getPerson().getCity());
                personPut.setZip(putPerson.getPerson().getZip());
                personPut.setPhone(putPerson.getPerson().getPhone());
                personPut.setEmail(putPerson.getPerson().getEmail());

                personsList.add(personPut);
        safetyNetRepository.getData().getMedicalrecords().stream().filter(x -> !putPerson.getMedical().getFirstName().equals(x.getFirstName())
                && !putPerson.getMedical().getLastName().equals(x.getLastName())).forEach(medicalRecordsList::add);

                MedicalRecords medicalRecords = new MedicalRecords();

                medicalRecords.setFirstName(putPerson.getMedical().getFirstName());
                medicalRecords.setLastName(putPerson.getMedical().getLastName());
                medicalRecords.setBirthdate(putPerson.getMedical().getBirthdate());
                medicalRecords.setMedications(putPerson.getMedical().getMedications());
                medicalRecords.setAllergies(putPerson.getMedical().getAllergies());

                medicalRecordsList.add(medicalRecords);

        List<FireStations> fireStationsList = new ArrayList<>(safetyNetRepository.getData().getFirestations());

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);

        return listSafety;
    }
}
