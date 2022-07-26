package com.safety.safetyNet.service;


import com.safety.safetyNet.configuration.SafetyNetConfiguration;
import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.model.PersonsChildren;
import com.safety.safetyNet.repository.SafetyNetMedicalRecordsRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
public class SafetyNetChildrenByAddress {

    @Autowired
    SafetyNetCalculatorAgeBirthdate safetyNetCalculatorAgeBirthdate;
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetMedicalRecordsRepository safetyNetMedicalRecordsRepository;
    @Autowired
    SafetyNetConfiguration safetyNetConfiguration;

    public List<PersonsChildren> getChildrenByAddress(String address) {
        String pathFile = safetyNetConfiguration.getPathFile();
        List<PersonsChildren> listMineur = new ArrayList<>();
        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(pathFile);
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);

        for (MedicalRecords medic : dataMedical) {
            long yearBirth = safetyNetCalculatorAgeBirthdate.calculeDateBirthdate(medic.getBirthdate());
            if (yearBirth <= 18) {
                for (Persons person : dataPersons) {

                    if (medic.getFirstName().equals(person.getFirstName()) && medic.getLastName()
                            .equals(person.getLastName()) && address.equals(person.getAddress())) {
                        PersonsChildren personsChildren = new PersonsChildren();
                        personsChildren.setLastName(person.getLastName());
                        personsChildren.setFirstName(person.getFirstName());
                        personsChildren.setAge(yearBirth);
                        listMineur.add(personsChildren);
                    }
                }
            }
        }

        return listMineur;
    }
}
