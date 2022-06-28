package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

            if (newPerson.getMedications() != null) {
                String[] keySplittedMedications = newPerson.getMedications().split(",");
                medicationsList.addAll(Arrays.asList(keySplittedMedications));
            }
            if (newPerson.getAllergies() != null) {
                String[] keySplittedAllergies = newPerson.getAllergies().split(",");
                allergiesList.addAll(Arrays.asList(keySplittedAllergies));
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

    public ListSafety deletePerson(DeletePerson deletePerson) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<MedicalRecords> dataMedicalRecords = data.getMedicalrecords();
        List<FireStations> dataFireStation = data.getFirestations();
        List<Persons> personsList = new ArrayList<>();
        List<FireStations> fireStationsList = new ArrayList<>();
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        ListSafety listSafety = new ListSafety();

        List<Persons> personsStream = dataPersons.stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).collect(Collectors.toList());

        List<MedicalRecords> medicalStream = dataMedicalRecords.stream().filter(x -> !deletePerson.getFirstName().equals(x.getFirstName())
                && !deletePerson.getLastName().equals(x.getLastName())).collect(Collectors.toList());

        List<FireStations> fireStationsStream = dataFireStation.stream().collect(Collectors.toList());

                personsStream.forEach(persons -> personsList.add(persons));
                medicalStream.forEach(medicalRecords -> medicalRecordsList.add(medicalRecords));
                fireStationsStream.forEach(fireStations -> fireStationsList.add(fireStations));


        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecordsList);

        return listSafety;
    }

    public ListSafety putPerson(NewPerson putPerson) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<MedicalRecords> dataMedicalRecords = data.getMedicalrecords();
        List<FireStations> dataFireStation = data.getFirestations();

        ArrayList<Persons> personsList = new ArrayList<>();
        ArrayList<FireStations> fireStationsList = new ArrayList<>();
        ArrayList<MedicalRecords> medicalRecordsList = new ArrayList<>();

        ListSafety listSafety = new ListSafety();
        for (Persons person : dataPersons) {
            if (putPerson.getFirstName().equals(person.getFirstName()) && putPerson.getLastName().equals(person.getLastName())) {

                Persons persons = new Persons();

                persons.setFirstName(person.getFirstName());
                persons.setLastName(person.getLastName());
                persons.setAddress(putPerson.getAddress());
                persons.setCity(putPerson.getCity());
                persons.setZip(putPerson.getZip());
                persons.setPhone(putPerson.getPhone());
                persons.setEmail(putPerson.getEmail());
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

            }
        }
        for (MedicalRecords medical : dataMedicalRecords) {
            if (putPerson.getFirstName().equals(medical.getFirstName()) && putPerson.getLastName().equals(medical.getLastName())) {
                MedicalRecords medicalRecords = new MedicalRecords();

                List<String> medicationsList = new ArrayList<>();
                List<String> allergiesList = new ArrayList<>();

                medicalRecords.setFirstName(medical.getFirstName());
                medicalRecords.setLastName(medical.getLastName());
                medicalRecords.setBirthdate(putPerson.getBirthdate());
                medicalRecords.setMedications(medicationsList);

                if (putPerson.getMedications() != null) {
                    String[] keySplittedMedications = putPerson.getMedications().split(",");
                    medicationsList.addAll(Arrays.asList(keySplittedMedications));
                    medicalRecords.setMedications(medicationsList);
                }
                if (putPerson.getAllergies() != null) {
                    String[] keySplittedAllergies = putPerson.getAllergies().split(",");
                    allergiesList.addAll(Arrays.asList(keySplittedAllergies));
                    medicalRecords.setAllergies(allergiesList);
                }
                medicalRecords.setAllergies(allergiesList);
                medicalRecordsList.add(medicalRecords);
            } else {
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
