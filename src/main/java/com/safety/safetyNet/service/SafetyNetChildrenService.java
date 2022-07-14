package com.safety.safetyNet.service;

import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.model.PersonsChildren;
import com.safety.safetyNet.repository.SafetyNetChildrenRepository;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetChildrenService {

    @Autowired
    SafetyNetRepository safetyNetRepository;
    @Autowired
    SafetyNetChildrenRepository safetyNetChildrenRepository;

    /**
     * fonction pour trier les mineurs de 18 ans et moins en fonction de leur adresse.
     *
     * @param address l'adresse de la résidence
     * @return liste de mineur
     */
    public List<PersonsChildren> getChildrenThisAddress(String address) {

        List<Persons> dataPersons = safetyNetRepository.getData().getPersons();
        List<PersonsChildren> listMineur = new ArrayList<>();
        List<PersonsChildren> personsChildren = safetyNetChildrenRepository.getChildrenRepository();

        for (PersonsChildren medic : personsChildren) {
            for (Persons person : dataPersons) {
                if (medic.getFirstName().equals(person.getFirstName()) && medic.getLastName()
                        .equals(person.getLastName()) && address.equals(person.getAddress())) {
                    PersonsChildren persons = new PersonsChildren();
                    persons.setLastName(person.getLastName());
                    persons.setFirstName(person.getFirstName());
                    persons.setAge(medic.getAge());
                    listMineur.add(persons);
                }
            }
        }
        return listMineur;
    }
}
