package com.safety.safetyNet.service;


import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author o.froidefond
 */
@Slf4j
@Service
public class SafetyNetService {

    @Autowired
    SafetyNetRepository safetyNetRepository;

    /**
     * Fonction pour récupérer tout les mails d'une ville.
     *
     * @param city le nom de la ville
     * @return Une liste de mail trié
     */
    public ResponseEmail getAllMail(String city) {

        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();

        ArrayList<String> listEmail = new ArrayList<>();
        TreeSet<String> treeMail = new TreeSet<>();
        ResponseEmail responseEmail = new ResponseEmail();


        for (Persons dataMail : dataPersons) {
            if (city.equals(dataMail.getCity())) {
                treeMail.add(dataMail.getEmail());
            }
        }
        for (String mailTree : treeMail) {
            Email mail = new Email();
            listEmail.add(mailTree);
            mail.setEmail(listEmail);
        }
        responseEmail.setMail(listEmail);
        return responseEmail;

    }

    /**
     * fonction pour triée les habitant en fonction de la caserne de pompier.
     *
     * @param stationNumber numéro de la caserne de pompier.
     * @return la liste des personnes qui habite autour de la caserne de pompier.
     */
    public ResponsePersonsStation getAllPersonsWithStationNumber(int stationNumber) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<MedicalRecords> dataMedical = data.getMedicalrecords();
        ArrayList<PersonsStation> listPersons = new ArrayList<>();
        ResponsePersonsStation responsePersonsStation = new ResponsePersonsStation();
        CountPeople countPeople = new CountPeople();

        int countAdult = 0;
        int countChildren = 0;

        for (Firestations firestation : dataFireStations) {
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
     * fonction pour trier les mineurs de 18 ans et moins en fonction de leur adresse.
     *
     * @param address l'adresse de la résidence
     * @return liste de mineur
     */
    public ResponsePersonsChildren getChildrenThisAddress(String address) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<MedicalRecords> dataMedical = data.getMedicalrecords();
        ArrayList<Persons> dataPersons = data.getPersons();

        List<PersonsChildren> listMineur = new ArrayList<>();
        ResponsePersonsChildren responsePersonsChildren = new ResponsePersonsChildren();

        for (MedicalRecords medic : dataMedical) {
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
                    for (Persons person : dataPersons) {
                        if (medic.getFirstName().equals(person.getFirstName()) && medic.getLastName()
                                .equals(person.getLastName()) && address.equals(person.getAddress())) {
                            PersonsChildren persons = new PersonsChildren();
                            persons.setLastName(person.getLastName());
                            persons.setFirstName(person.getFirstName());
                            persons.setAge(yearBirth);
                            listMineur.add(persons);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        responsePersonsChildren.setChildren(listMineur);
        return responsePersonsChildren;
    }

    /**
     * fonction pour récupérer les numéro de téléphone en fonction de la caserne de pompier
     *
     * @param stationNumber numéro de la caserne de pompier
     * @return liste de numéro de téléphone unique
     */
    public ResponsePhone getNumberPhoneThisFireStation(int stationNumber) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();

        List<Phone> listPhone = new ArrayList<>();
        TreeSet<String> treePhone = new TreeSet<>();
        ResponsePhone responsePhone = new ResponsePhone();


        for (Firestations firestation : dataFireStations) {
            int station = Integer.parseInt(firestation.getStation());
            if (stationNumber == station) {
                for (Persons person : dataPersons) {
                    if (person.getAddress().equals(firestation.getAddress())) {
                        treePhone.add(person.getPhone());
                    }
                }
            }
        }
        for (String phone : treePhone) {
            Phone phoneTree = new Phone();
            phoneTree.setPhone(phone);
            listPhone.add(phoneTree);
        }

        responsePhone.setPhone(listPhone);
        return responsePhone;
    }

    /**
     * fonction pour récupérer les personne en fonction de leur adresse et indiquer
     * quel caserne de pompier qui les desserve.
     *
     * @param address l'adresse des habitant
     * @return liste de personne
     */
    public ResponsePersonsMedical getPersonsThisAddressPlusStationNumber(String address) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<MedicalRecords> dateMedicalRecords = data.getMedicalrecords();
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
            for (Firestations station : dataFireStations) {
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

    public ResponsePersonsFireStation getHouseServeFireStation(int fireStation) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<MedicalRecords> dateMedicalRecords = data.getMedicalrecords();
        List<PersonsFireStation> TabListPersons = new ArrayList<>();
        TreeSet<String> listLastName = new TreeSet<>();
        ResponsePersonsFireStation responsePersonsFirestation = new ResponsePersonsFireStation();

        for (Firestations firestation : dataFireStations) {
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
                                    TabListPersons.add(persons);

                                } catch (Exception e){
                                    log.error("error :", e);
                                }
                            }
                        }
                    }
                }
            }
        }
        responsePersonsFirestation.setPersonsFireStations(TabListPersons);
        return responsePersonsFirestation;
    }


}
