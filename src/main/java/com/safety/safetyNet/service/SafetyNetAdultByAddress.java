package com.safety.safetyNet.service;

import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.model.PersonsAdult;
import com.safety.safetyNet.repository.SafetyNetMedicalRecordsRepository;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;

@Service
public class SafetyNetAdultByAddress {
    @Autowired
    SafetyNetMedicalRecordsRepository safetyNetMedicalRecordsRepository;
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetCalculatorAgeBirthdate safetyNetCalculatorAgeBirthdate;


    public List<PersonsAdult> getAdultByAddress(String address){

        List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);


        List<PersonsAdult> personsAdultList = new ArrayList<>();

        List<Persons> personsByAddress = dataPersons.stream().filter(person -> address.equals(person.getAddress())).collect(Collectors.toList());

        for (MedicalRecords medic : dataMedical) {
            long yearBirth = safetyNetCalculatorAgeBirthdate.calculeDateBirthdate(medic.getBirthdate());
            if (yearBirth > 18) {
                for (Persons person : personsByAddress) {
                    if (medic.getFirstName().equals(person.getFirstName()) && medic.getLastName()
                            .equals(person.getLastName())) {
                        PersonsAdult personsAdult = new PersonsAdult();

                        personsAdult.setLastName(person.getLastName());
                        personsAdult.setFirstName(person.getFirstName());

                        personsAdultList.add(personsAdult);
                    }
                }
            }
        }
        return personsAdultList;
    }




}
