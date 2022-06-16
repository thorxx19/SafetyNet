package com.safety.safetyNet.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;


@Slf4j
public class SafetyRepository {

    public ListSafety test() {


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {

            ListSafety data = mapper.readValue(new File("src/main/java/com/safety/safetyNet/config/data.json"), ListSafety.class);
            //log.info(String.valueOf(data.getFirestations()));
            //log.info(String.valueOf(data.getMedicalrecords()));
            //log.info(String.valueOf(data.getPersons()));
            //log.info(String.valueOf(data));


            String resultJ = "";
            for (Object res : data.getPersons()) {
                resultJ = res.toString();

                if (resultJ.contains("address=112 Steppes Pl")) {
                    log.info(res.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
