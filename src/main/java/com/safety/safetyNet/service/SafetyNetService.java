package com.safety.safetyNet.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;


@Slf4j
public class SafetyNetService {


    public ListSafety test(){


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {

            ListSafety data = mapper.readValue(new File("data.json"),ListSafety.class);
            log.info(String.valueOf(data.getFirestations()));

            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

    return null;
    }

}
