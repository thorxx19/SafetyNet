package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetChildrenService {

    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetChildrenByAddress safetyNetChildrenByAddress;
    @Autowired
    SafetyNetAdultByAddress safetyNetAdultByAddress;

    /**
     * fonction pour trier les mineurs de 18 ans et moins en fonction de leur adresse.
     *
     * @param address l'adresse de la résidence
     * @return liste de mineur
     */
    public List<PersonsByAddress> getChildrenByAddress(String address) {

        List<PersonsChildren> personsChildren = safetyNetChildrenByAddress.getChildrenRepository(address);
        List<PersonsAdult> personsAdults = safetyNetAdultByAddress.getAdultByAddress(address);
        List<PersonsByAddress> responseChildByAddress = new ArrayList<>();
        List<Siblings> siblingsList = new ArrayList<>();

        TreeSet treeSet = new TreeSet<>();

        for (PersonsChildren children: personsChildren) {
            treeSet.add(children.getLastName());
        }
        for (Object x : treeSet) {
            Siblings siblings = new Siblings();
            List<PersonsChildren> childrenList = new ArrayList<>();
            for (PersonsChildren children : personsChildren) {
                if (x.equals(children.getLastName())) {

                    PersonsChildren personsChildren1 = new PersonsChildren();
                    personsChildren1.setLastName(children.getLastName());
                    personsChildren1.setFirstName(children.getFirstName());
                    personsChildren1.setAge(children.getAge());

                    childrenList.add(personsChildren1);
                    siblings.setChildren(childrenList);
                }
            }
            siblingsList.add(siblings);
        }
        for (Siblings sibling : siblingsList) {
            PersonsByAddress persons = new PersonsByAddress();
            for (PersonsChildren children: sibling.getChildren()) {
                List<PersonsAdult> personsAdultList = new ArrayList<>();

                for (PersonsAdult adult: personsAdults) {
                    if (children.getLastName().equals(adult.getLastName())){
                        PersonsAdult personsAdult = new PersonsAdult();
                        personsAdult.setFirstName(adult.getFirstName());
                        personsAdult.setLastName(adult.getLastName());

                        personsAdultList.add(personsAdult);
                    }
                }
                persons.setAdult(personsAdultList);
                persons.setSiblings(sibling);
            }
            responseChildByAddress.add(persons);
        }
        return responseChildByAddress;
    }
}
