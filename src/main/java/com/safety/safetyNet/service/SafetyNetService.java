package com.safety.safetyNet.service;



import com.safety.safetyNet.model.Email;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
     * @param city le nom de la ville
     * @return Une liste de mail trié
     */
    public List<Email> getAllMail(String city){

     ListSafety data = safetyNetRepository.getData();
     ArrayList<Persons> dataPersons = data.getPersons();

        ArrayList<Email> listEmail = new ArrayList<>();



        //todo garder que les mail unique
        if (data != null) {
            for (Persons dataMail : dataPersons) {

                if (city.equals(dataMail.getCity())){
                    Email mail = new Email();
                    mail.setEmail(dataMail.getEmail());

                    listEmail.add(mail);
                }
            }

        } else {
            return null;
        }

      return listEmail;
    }
}
