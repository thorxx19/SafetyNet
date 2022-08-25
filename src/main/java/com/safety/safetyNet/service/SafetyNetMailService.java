package com.safety.safetyNet.service;


import com.safety.safetyNet.configuration.SafetyNetConfiguration;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetPersonsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;


/**
 * @author o.froidefond
 */
@Service
@Slf4j
public class SafetyNetMailService {
    @Autowired
    SafetyNetPersonsRepository safetyNetPersonsRepository;
    @Autowired
    SafetyNetConfiguration safetyNetConfiguration;
    /**
     * Fonction pour récupérer tout les mails d'une ville.
     *
     * @param city le nom de la ville
     * @return Une liste de mail trié
     */
    public TreeSet<String> getMailByCity(String city) {
        log.debug("Start getMailByCity");
        String pathFile = safetyNetConfiguration.getPathFile();
        List<Persons> dataPersons = safetyNetPersonsRepository.getPerson(pathFile);
        TreeSet<String> treeMail = new TreeSet<>();

        for (Persons dataMail : dataPersons) {
            if (city.equals(dataMail.getCity())) {
                treeMail.add(dataMail.getEmail());
            }
        }
        log.debug("End getMailByCity");
        return treeMail;
    }
}
