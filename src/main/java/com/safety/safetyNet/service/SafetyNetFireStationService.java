package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SafetyNetFireStationService {

    @Autowired
    SafetyNetRepository safetyNetRepository;

    /**
     * fonction pour triée les habitant en fonction de la caserne de pompier.
     *
     * @param stationNumber numéro de la caserne de pompier.
     * @return la liste des personnes qui habite autour de la caserne de pompier.
     */
    public ResponsePersonsStation getAllPersonsWithStationNumber(int stationNumber) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<FireStations> dataFireStations = data.getFirestations();
        List<MedicalRecords> dataMedical = data.getMedicalrecords();
        ArrayList<PersonsStation> listPersons = new ArrayList<>();
        ResponsePersonsStation responsePersonsStation = new ResponsePersonsStation();
        CountPeople countPeople = new CountPeople();

        int countAdult = 0;
        int countChildren = 0;

        for (FireStations firestation : dataFireStations) {
            int station = Integer.parseInt(firestation.getStation());
            if (stationNumber == station) {
                for (Persons person : dataPersons) {
                    if (person.getAddress().equals(firestation.getAddress())) {
                        for (MedicalRecords medic : dataMedical) {
                            if (person.getLastName().equals(medic.getLastName()) && person.getFirstName().equals(medic.getFirstName())) {
                                Calendar today = Calendar.getInstance();
                                SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date birth = dateTimeFormatter.parse(medic.getBirthdate());
                                    Date todayParse = today.getTime();
                                    long result = todayParse.getTime() - birth.getTime();
                                    TimeUnit time = TimeUnit.DAYS;
                                    long resultDay = time.convert(result, TimeUnit.MILLISECONDS);
                                    long yearBirth = resultDay / 365;
                                    if (yearBirth <= 18) {
                                        countChildren++;
                                    } else {
                                        countAdult++;
                                    }
                                } catch (Exception e) {
                                    log.info(e.getMessage());
                                }
                            }
                        }
                        PersonsStation persons = new PersonsStation();
                        persons.setFirstName(person.getFirstName());
                        persons.setLastName(person.getLastName());
                        persons.setAddress(person.getAddress());
                        persons.setPhone(person.getPhone());
                        listPersons.add(persons);
                    }
                }
            }
        }
        if (countAdult != 0 || countChildren != 0) {
            countPeople.setChildren(countChildren);
            countPeople.setAdult(countAdult);
        }

        responsePersonsStation.setCountPeople(countPeople);
        responsePersonsStation.setPersonsStationList(listPersons);

        return responsePersonsStation;

    }

    /**
     * fonction pour récupérer une liste de personnes en fonction de leur adresse et indiquer
     * quel caserne de pompier qui les desserve.
     *
     * @param address l'adresse des habitant
     * @return liste de personne
     */
    public ResponsePersonsMedical getPersonsThisAddressPlusStationNumber(String address) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<FireStations> dataFireStations = data.getFirestations();
        List<MedicalRecords> dateMedicalRecords = data.getMedicalrecords();
        TreeSet<String> stationTree = new TreeSet<>();
        ResponsePersonsMedical responsePersonsMedical = new ResponsePersonsMedical();
        Station treeStation = new Station();

        List<PersonsMedical> listPersons = new ArrayList<>();
        for (Persons person : dataPersons) {
            if (address.equals(person.getAddress())) {
                for (MedicalRecords medic : dateMedicalRecords) {
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
                            PersonsMedical persons = new PersonsMedical();
                            persons.setLastName(person.getLastName());
                            persons.setPhone(person.getPhone());
                            persons.setAge(yearBirth);
                            persons.setAllergies(medic.getAllergies());
                            persons.setMedications(medic.getMedications());
                            listPersons.add(persons);

                        } catch (Exception e) {
                            log.error("error :", e);
                        }

                    }
                }
            }
            for (FireStations station : dataFireStations) {
                if (address.equals(station.getAddress())) {

                    stationTree.add(station.getStation());
                    treeStation.setStation(station.getStation());
                }
            }
        }
        responsePersonsMedical.setPersonsMedicals(listPersons);
        responsePersonsMedical.setStation(treeStation);

        return responsePersonsMedical;
    }

    /**
     * fonction pour récupérer une liste de personnes en fonction de leur caserne de pompier.
     *
     * @param fireStation n° de caserne de pompier
     * @return une liste de personnes
     */
    public List<PersonsFireStation> getHouseServeFireStation(int fireStation) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<FireStations> dataFireStations = data.getFirestations();
        List<MedicalRecords> dateMedicalRecords = data.getMedicalrecords();
        List<PersonsFireStation> tabListPersons = new ArrayList<>();
        TreeSet<String> listLastName = new TreeSet<>();

        for (FireStations firestation : dataFireStations) {
            int station = Integer.parseInt(firestation.getStation());
            if (fireStation == station) {
                for (Persons person : dataPersons) {
                    listLastName.add(person.getLastName());
                    if (person.getAddress().equals(firestation.getAddress())) {
                        for (MedicalRecords medic : dateMedicalRecords) {
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

                                    PersonsFireStation persons = new PersonsFireStation();
                                    persons.setLastName(person.getLastName());
                                    persons.setAddress(person.getAddress());
                                    persons.setAge(yearBirth);
                                    persons.setEmail(person.getEmail());
                                    persons.setAllergies(medic.getAllergies());
                                    persons.setMedications(medic.getMedications());
                                    tabListPersons.add(persons);

                                } catch (Exception e) {
                                    log.error("error :", e);
                                }
                            }
                        }
                    }
                }
            }
        }

        return tabListPersons;
    }
    public ListSafety postNewFireStation(FireStations fireStations){
        ListSafety listSafety = new ListSafety();
        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
        List<FireStations> fireStationsList = safetyNetRepository.getData().getFirestations();
        boolean verifFireStation = true;

        for (FireStations fireStation:fireStationsList) {
            if (fireStations.getAddress().equals(fireStation.getAddress())) {
                verifFireStation = false;
                break;
            }
        }
        if (verifFireStation){
            fireStationsList.add(fireStations);
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecords);

        return listSafety;
    }
    public ListSafety deleteFireStation(FireStations deleteFireStations){
        ListSafety listSafety = new ListSafety();
        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
        List<FireStations> fireStationsList = new ArrayList<>();

        safetyNetRepository.getData().getFirestations().stream().filter(x -> !deleteFireStations.getAddress().equals(x.getAddress()))
                .forEach(x -> fireStationsList.add(x));

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecords);

        return listSafety;
    }
    public ListSafety putFireStation(FireStations putFireStation){
        ListSafety listSafety = new ListSafety();
        List<MedicalRecords> medicalRecords = new ArrayList<>(safetyNetRepository.getData().getMedicalrecords());
        List<Persons> personsList = new ArrayList<>(safetyNetRepository.getData().getPersons());
        List<FireStations> fireStationsList = safetyNetRepository.getData().getFirestations();

        for (FireStations fireStation: fireStationsList) {
            if (putFireStation.getAddress().equals(fireStation.getAddress())){
                fireStation.setStation(putFireStation.getStation());
                break;
            }
        }

        listSafety.setPersons(personsList);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(medicalRecords);

        return listSafety;
    }
}
