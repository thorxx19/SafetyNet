package com.safety.safetyNet.service;



import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Slf4j
@Service
public class SafetyNetService {

    @Autowired
    SafetyNetRepository safetyNetRepository;

    public ArrayList<Persons> getAllMail(){

     ListSafety data = safetyNetRepository.getData();

     ArrayList<Persons> dataPersons = data.getPersons();

      return dataPersons;
    }




}
