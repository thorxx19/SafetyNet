package com.safety.safetyNet.service;

import com.safety.safetyNet.model.*;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
@Slf4j
public class SafetyNetPhoneService {
    @Autowired
    SafetyNetRepository safetyNetRepository;

    /**
     * fonction pour récupérer les numéro de téléphone en fonction de la caserne de pompier
     *
     * @param stationNumber numéro de la caserne de pompier
     * @return liste de numéro de téléphone unique
     */
    public ResponsePhone getNumberPhoneThisFireStation(int stationNumber) {
        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();
        List<FireStations> dataFireStations = data.getFirestations();

        ArrayList<String> listPhone = new ArrayList<>();
        TreeSet<String> treePhone = new TreeSet<>();
        ResponsePhone responsePhone = new ResponsePhone();


        for (FireStations firestation : dataFireStations) {
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

            listPhone.add(phone);
            phoneTree.setPhone(listPhone);
        }

        responsePhone.setPhone(listPhone);
        return responsePhone;
    }
}
