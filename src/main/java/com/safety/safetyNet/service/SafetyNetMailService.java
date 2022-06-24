package com.safety.safetyNet.service;

import com.safety.safetyNet.model.Email;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.model.ResponseEmail;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
@Slf4j
public class SafetyNetMailService {
    @Autowired
    SafetyNetRepository safetyNetRepository;

    /**
     * Fonction pour récupérer tout les mails d'une ville.
     *
     * @param city le nom de la ville
     * @return Une liste de mail trié
     */
    public ResponseEmail getAllMail(String city) {

        ListSafety data = safetyNetRepository.getData();
        List<Persons> dataPersons = data.getPersons();

        ArrayList<String> listEmail = new ArrayList<>();
        TreeSet<String> treeMail = new TreeSet<>();
        ResponseEmail responseEmail = new ResponseEmail();


        for (Persons dataMail : dataPersons) {
            if (city.equals(dataMail.getCity())) {
                treeMail.add(dataMail.getEmail());
            }
        }
        for (String mailTree : treeMail) {
            Email mail = new Email();
            listEmail.add(mailTree);
            mail.setEmail(listEmail);
        }
        responseEmail.setMail(listEmail);
        return responseEmail;

    }
}