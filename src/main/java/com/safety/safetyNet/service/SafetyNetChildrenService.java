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

/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetChildrenService {

    @Autowired
    SafetyNetRepository safetyNetRepository;

    /**
     * fonction pour trier les mineurs de 18 ans et moins en fonction de leur adresse.
     *
     * @param address l'adresse de la résidence
     * @return liste de mineur
     */
    public List<PersonsChildren> getChildrenThisAddress(String address) {
        ListSafety data = safetyNetRepository.getData();
        List<MedicalRecords> dataMedical = data.getMedicalrecords();
        List<Persons> dataPersons = data.getPersons();
        List<PersonsChildren> listMineur = new ArrayList<>();


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
                log.error("error :",e);
            }
        }

        return listMineur;
    }
}
