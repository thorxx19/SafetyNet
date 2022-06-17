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
import java.util.TreeSet;
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
    public Object getAllMail(String city) {

        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();

        ArrayList<Object> listEmail = new ArrayList<>();
        TreeSet<String> treeMail = new TreeSet<>();
        String mailString = "";


        for (Persons dataMail : dataPersons) {
            if (city.equals(dataMail.getCity())) {
                mailString = dataMail.getEmail();
                treeMail.add(mailString);
            }
        }
        for (String mailTree : treeMail) {
            Email mail = new Email();
            mail.setEmail(mailTree);
            listEmail.add(mail);
        }
        if (listEmail.isEmpty()) {
            MessageError message = new MessageError();
            message.setMessage("il y a pas donnée pour la ville de " + city + "");
            message.setError("error name of city");
            listEmail.add(message);
        }
        return listEmail;

    }

    /**
     * fonction pour triée les habitant en fonction de la caserne de pompier.
     *
     * @param stationNumber numéro de la caserne de pompier.
     * @return la liste des personnes qui habite autour de la caserne de pompier.
     */
    public Object getAllPersonsWithStationNumber(int stationNumber) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<MedicalRecords> dataMedical = data.getMedicalrecords();
        ArrayList<Object> listPersons = new ArrayList<>();
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
            CountPeople countPeople = new CountPeople();
            countPeople.setChildren(countChildren);
            countPeople.setAdult(countAdult);
            listPersons.add(countPeople);
        }
        if (listPersons.isEmpty()) {
            MessageError message = new MessageError();
            message.setMessage("sation n° " + stationNumber + " non implémenter");
            message.setError("error numéro de caserne de pompier");
            listPersons.add(message);
        }
        return listPersons;

    }

    /**
     * fonction pour trier les mineurs de 18 ans et moins en fonction de leur adresse.
     *
     * @param address l'adresse de la résidence
     * @return liste de mineur
     */
    public Object getChildrenThisAddress(String address) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<MedicalRecords> dataMedical = data.getMedicalrecords();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Object> listMineur = new ArrayList<>();
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
        if (listMineur.isEmpty()) {
            MessageError message = new MessageError();
            message.setMessage("l'adresse de cette enfant n'est pas trouver");
            message.setError("error d'adresse");
            listMineur.add(message);
        }
        return listMineur;
    }

    /**
     * fonction pour récupérer les numéro de téléphone en fonction de la caserne de pompier
     *
     * @param stationNumber numéro de la caserne de pompier
     * @return liste de numéro de téléphone unique
     */
    public Object getNumberPhoneThisFireStation(int stationNumber) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<Object> listPhone = new ArrayList<>();
        TreeSet<String> treePhone = new TreeSet<>();

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
        if (listPhone.isEmpty()) {

            MessageError message = new MessageError();
            message.setMessage("pas d'habitant enregistrée pour la staion " + stationNumber + "");
            message.setError("error pas de numéro de téléphone ");
            listPhone.add(message);
        }
        return listPhone;
    }

    /**
     * fonction pour récupérer les personne en fonction de leur adresse et indiquer
     * quel caserne de pompier qui les desserve.
     *
     * @param address l'adresse des habitant
     * @return liste de personne
     */
    public Object getPersonsThisAddressPlusStationNumber(String address) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<MedicalRecords> dateMedicalRecords = data.getMedicalrecords();
        TreeSet<String> stationTree = new TreeSet<>();

        ArrayList<Object> listPersons = new ArrayList<>();
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
                            log.info(e.getMessage());
                        }

                    }
                }
            }
            for (Firestations station : dataFireStations) {
                if (address.equals(station.getAddress())) {
                    stationTree.add(station.getStation());
                }
            }
        }
        if (!stationTree.isEmpty()) {
            Station station = new Station();
            station.setStation(stationTree.first());
            listPersons.add(station);
        }
        if (listPersons.isEmpty()) {
            MessageError message = new MessageError();
            message.setMessage("il n'y pas d'habitant a l'adresse " + address + "");
            message.setError("error d'adresse");
            listPersons.add(message);
        }
        return listPersons;
    }

    public Object getHouseServeFireStation(int fireStation) {
        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<MedicalRecords> dateMedicalRecords = data.getMedicalrecords();
        ArrayList<Object> listPersons = new ArrayList<>();
        TreeSet<String> listLastName = new TreeSet<>();

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
                                    listPersons.add(persons);

                                } catch (Exception e){
                                    log.error(e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
        if (listPersons.isEmpty()) {
            MessageError message = new MessageError();
            message.setMessage("pas d'habitant pour cette caserne de pompier N° " + fireStation + "");
            message.setError("error number stations");
            listPersons.add(message);
        }

        return listPersons;
    }


}
