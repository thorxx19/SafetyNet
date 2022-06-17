package com.safety.safetyNet.service;


import com.safety.safetyNet.model.Email;
import com.safety.safetyNet.model.Firestations;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
    public List<Email> getAllMail(String city) {

        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();

        ArrayList<Email> listEmail = new ArrayList<>();
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
            return null;
        } else {
            return listEmail;
        }
    }

    /**
     * fonction pour triée les habitant en fonction de la caserne de pompier.
     * @param stationNumber numéro de la caserne de pompier.
     * @return la liste des personnes qui habite autour de la caserne de pompier.
     */
    public Object getAllPersonsWithStationNumber(int stationNumber) {

        ListSafety data = safetyNetRepository.getData();
        ArrayList<Persons> dataPersons = data.getPersons();
        ArrayList<Firestations> dataFireStations = data.getFirestations();
        ArrayList<Persons> listPersons = new ArrayList<>();

        for (Firestations firestation : dataFireStations) {
            int station = Integer.parseInt(firestation.getStation());
            if (stationNumber == station) {
                for (Persons person : dataPersons) {
                    if (person.getAddress().equals(firestation.getAddress())) {
                        Persons persons = new Persons();
                        persons.setFirstName(person.getFirstName());
                        persons.setLastName(person.getLastName());
                        persons.setAddress(person.getAddress());
                        persons.setCity(person.getCity());
                        persons.setZip(person.getZip());
                        persons.setPhone(person.getPhone());
                        persons.setEmail(person.getEmail());
                        listPersons.add(persons);
                    }
                }
            }
        }
        if (listPersons.isEmpty()) {
            return null;
        } else {
            return listPersons;
        }
    }


}
