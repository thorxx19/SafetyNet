package com.safety.safetyNet.repository;


import com.safety.safetyNet.model.MedicalRecords;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.model.PersonsChildren;
import com.safety.safetyNet.service.SafetyNetCalculatorAgeBirthdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;

@Slf4j
@Repository
public class SafetyNetChildrenRepository {

    @Autowired
    SafetyNetCalculatorAgeBirthdate safetyNetCalculatorAgeBirthdate;
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetMedicalRecordsRepository safetyNetMedicalRecordsRepository;

    public List<PersonsChildren> getChildrenRepository(){
        List<PersonsChildren> listMineur = new ArrayList<>();


            List<MedicalRecords> dataMedical = safetyNetMedicalRecordsRepository.getMedicalRecords(PATH_FILE);
            List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
            for (MedicalRecords medic : dataMedical) {
                long yearBirth = safetyNetCalculatorAgeBirthdate.calculeDateBirthdate(medic.getBirthdate());
                if (yearBirth <= 18) {
                    for (Persons person : dataPersons) {
                        if (medic.getFirstName().equals(person.getFirstName()) && medic.getLastName()
                                .equals(person.getLastName())) {
                            PersonsChildren persons = new PersonsChildren();
                            persons.setLastName(person.getLastName());
                            persons.setFirstName(person.getFirstName());
                            persons.setAge(yearBirth);
                            listMineur.add(persons);
                        }
                    }
                }
            }
            return listMineur;
    }
}
