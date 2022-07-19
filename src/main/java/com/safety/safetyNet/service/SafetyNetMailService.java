package com.safety.safetyNet.service;


import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;

/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetMailService {
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;


    /**
     * Fonction pour récupérer tout les mails d'une ville.
     *
     * @param city le nom de la ville
     * @return Une liste de mail trié
     */
    public TreeSet<String> getMailByCity(String city) {
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(PATH_FILE);
        TreeSet<String> treeMail = new TreeSet<>();

        for (Persons dataMail : dataPersons) {
            if (city.equals(dataMail.getCity())) {
                treeMail.add(dataMail.getEmail());
            }
        }

        return treeMail;

    }
}
