package com.safety.safetyNet.service;

import com.safety.safetyNet.configuration.SafetyNetConfiguration;
import com.safety.safetyNet.model.FireStations;
import com.safety.safetyNet.model.ResponseFireStationByNumber;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.model.PersonsStation;
import com.safety.safetyNet.model.CountPeople;
import com.safety.safetyNet.model.ResponsePersonsByAddress;
import com.safety.safetyNet.model.PersonsMedical;
import com.safety.safetyNet.model.Station;
import com.safety.safetyNet.model.ResponsePersonsByStationNumber;
import com.safety.safetyNet.model.PersonsFireStation;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.repository.SafetyNetFireStationRepository;
import com.safety.safetyNet.repository.SafetyNetMedicalRecordsRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * @author o.froidefond
 */
@Slf4j
@Service
public class SafetyNetFireStationService {


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
     * fonction pour triée les habitant en fonction de la caserne de pompier.
     *
     * @param stationNumber numéro de la caserne de pompier.
     * @return la liste des personnes qui habite autour de la caserne de pompier.
     */
    public List<ResponseFireStationByNumber> getAllPersonsByStationNumber(int stationNumber) {
        log.debug("Start getAllPersonsByStationNumber");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);

        ArrayList<PersonsStation> listPersons = new ArrayList<>();

        List<ResponseFireStationByNumber> responseFireStationByNumberArrayList = new ArrayList<>();
        CountPeople countPeople = new CountPeople();
        ResponseFireStationByNumber responseFireStationByNumber = new ResponseFireStationByNumber();
        int countAdult = 0;
        int countChildren = 0;

        for (FireStations firestation : dataFireStations) {
            int station = Integer.parseInt(firestation.getStation());
            if (stationNumber == station) {

                for (Persons person : dataPersons) {
                    if (person.getAddress().equals(firestation.getAddress())) {
                        for (MedicalRecords medic : dataMedical) {
                            if (person.getLastName().equals(medic.getLastName()) && person.getFirstName()
                                    .equals(medic.getFirstName())) {

                                long yearBirth = safetyNetCalculatorAgeBirthdate
                                        .calculeDateBirthdate(medic.getBirthdate());
                                if (yearBirth <= 18) {
                                    countChildren++;
                                    break;
                                } else {
                                    countAdult++;
                                    break;
                                }
                            }
                        }
                        PersonsStation persons = new PersonsStation();
                        persons.setFirstName(person.getFirstName());
                        persons.setLastName(person.getLastName());
                        persons.setAddress(person.getAddress());
                        persons.setPhone(person.getPhone());
                        listPersons.add(persons);
                        responseFireStationByNumber.setCountPeople(countPeople);
                        responseFireStationByNumber.setPersonsStationList(listPersons);
                    }
                }
            }
        }
        if (countAdult != 0 || countChildren != 0) {

            countPeople.setChildren(countChildren);
            countPeople.setAdult(countAdult);
            responseFireStationByNumberArrayList.add(responseFireStationByNumber);
        }

        log.debug("End getAllPersonsByStationNumber");
        return responseFireStationByNumberArrayList;

    }

    /**
     * fonction pour récupérer une liste de personnes en fonction de leur adresse et indiquer
     * quel caserne de pompier qui les desserve.
     *
     * @param address l'adresse des habitant
     * @return liste de personne
     */
    public List<ResponsePersonsByAddress> getPersonsByAddress(String address) {
        log.debug("Start getPersonsByAddress");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        List<ResponsePersonsByAddress> responsePersonsByAddressList = new ArrayList<>();


        List<PersonsMedical> listPersons = new ArrayList<>();


        for (FireStations station : dataFireStations) {
            if (address.equals(station.getAddress())) {
                ResponsePersonsByAddress responsePersonsByAddress = new ResponsePersonsByAddress();
                TreeSet<String> stationTree = new TreeSet<>();
                Station treeStation = new Station();
                stationTree.add(station.getStation());
                treeStation.setStation(station.getStation());
                responsePersonsByAddress.setStation(treeStation);
                for (Persons person : dataPersons) {
                    if (address.equals(person.getAddress())) {
                        for (MedicalRecords medic : dataMedical) {
                            if (person.getLastName().equals(medic.getLastName()) && person.getFirstName()
                                    .equals(medic.getFirstName())) {

                                long yearBirth = safetyNetCalculatorAgeBirthdate
                                        .calculeDateBirthdate(medic.getBirthdate());
                                PersonsMedical persons = new PersonsMedical();
                                persons.setLastName(person.getLastName());
                                persons.setPhone(person.getPhone());
                                persons.setAge(yearBirth);
                                persons.setAllergies(medic.getAllergies());
                                persons.setMedications(medic.getMedications());
                                listPersons.add(persons);

                            }
                            responsePersonsByAddress.setPersonsMedicals(listPersons);

                        }
                    }
                }
                responsePersonsByAddressList.add(responsePersonsByAddress);
            }
        }

        log.debug("End getPersonsByAddress");
        return responsePersonsByAddressList;
    }

    /**
     * fonction pour récupérer une liste de personnes en fonction de leur caserne de pompier.
     *
     * @param fireStation n° de caserne de pompier
     * @return une liste de personnes
     */
    public List<ResponsePersonsByStationNumber> getPersonsByStationNumber(String fireStation) {
        log.debug("Start getPersonsByStationNumber");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        List<ResponsePersonsByStationNumber> responseListPersonsByStationNumber = new ArrayList<>();
        List<FireStations> fireStationsList = dataFireStations.stream()
                .filter(fireStations -> fireStation.equals(fireStations.getStation())).collect(Collectors.toList());


        for (FireStations fireStations : fireStationsList) {
            ResponsePersonsByStationNumber responsePersonsByStationNumber = new ResponsePersonsByStationNumber();
            List<PersonsFireStation> tabListPersons = new ArrayList<>();
            for (Persons person : dataPersons) {
                if (fireStations.getAddress().equals(person.getAddress())) {
                    PersonsFireStation personsFireStation = new PersonsFireStation();
                    for (MedicalRecords medicalRecord : dataMedical) {
                        if (medicalRecord.getLastName().equals(person.getLastName())
                                && medicalRecord.getFirstName().equals(person.getFirstName())) {
                            long yearBirth = safetyNetCalculatorAgeBirthdate
                                    .calculeDateBirthdate(medicalRecord.getBirthdate());

                            responsePersonsByStationNumber.setAddress(person.getAddress());
                            personsFireStation.setLastName(person.getLastName());
                            personsFireStation.setEmail(person.getEmail());
                            personsFireStation.setAge(yearBirth);
                            personsFireStation.setMedications(medicalRecord.getMedications());
                            personsFireStation.setAllergies(medicalRecord.getAllergies());

                        }
                    }
                    tabListPersons.add(personsFireStation);
                }
            }
            responsePersonsByStationNumber.setPersonsByFireStations(tabListPersons);
            responseListPersonsByStationNumber.add(responsePersonsByStationNumber);
        }
        log.debug("End getPersonsByStationNumber");
        return responseListPersonsByStationNumber;
    }

    /**
     * Fonction pour ajouter une nouvelle caserne de pompiers dans l'objet listSafety.
     *
     * @param fireStations un objet de type FireStations.
     * @return un objet de type ListSafety.
     */
    public ListSafety postNewFireStation(FireStations fireStations) {
        log.debug("Start postNewFireStation");
        String pathFile = safetyNetConfiguration.getPathFile();
        ListSafety listSafety = new ListSafety();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        boolean verifFireStation = true;

        for (FireStations fireStation : dataFireStations) {
            if (fireStations.getAddress().equals(fireStation.getAddress())) {
                verifFireStation = false;
                break;
            }
        }
        if (verifFireStation) {
            dataFireStations.add(fireStations);
        }

        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);
        log.debug("End postNewFireStation");
        return listSafety;
    }

    /**
     * Fonction pour supprimer une caserne de pompiers dans l'objet listSafety.
     *
     * @param deleteFireStations un objet de type FireStations.
     * @return un objet de type ListSafety.
     */
    public ListSafety deleteFireStation(FireStations deleteFireStations) {
        log.debug("Start deleteFireStation");
        String pathFile = safetyNetConfiguration.getPathFile();
        ListSafety listSafety = new ListSafety();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        List<FireStations> fireStationsList = new ArrayList<>();

        dataFireStations.stream().filter(x -> !deleteFireStations.getAddress().equals(x.getAddress()))
                .forEach(x -> fireStationsList.add(x));

        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(fireStationsList);
        listSafety.setMedicalrecords(dataMedical);
        log.debug("End deleteFireStation");
        return listSafety;
    }

    /**
     * Fonction pour modifier une caserne de pompier dans l'objet listSafety.
     *
     * @param putFireStation un objet de type FireStations.
     * @return un objet de type ListSafety.
     */
    public ListSafety putFireStation(FireStations putFireStation) {
        log.debug("Start putFireStation");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        List<FireStations> dataFireStations = safetyNetFireStationRepository.getFireStation(pathFile);
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        ListSafety listSafety = new ListSafety();


        for (FireStations fireStation : dataFireStations) {
            if (putFireStation.getAddress().equals(fireStation.getAddress())) {
                fireStation.setStation(putFireStation.getStation());
                break;
            }
        }

        listSafety.setPersons(dataPersons);
        listSafety.setFirestations(dataFireStations);
        listSafety.setMedicalrecords(dataMedical);
        log.debug("End putFireStation");
        return listSafety;
    }
}
