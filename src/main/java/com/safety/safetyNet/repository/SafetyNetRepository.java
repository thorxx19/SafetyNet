package com.safety.safetyNet.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;


@Slf4j
@Repository
public class SafetyNetRepository {

    public ListSafety test() {


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {

            ListSafety data = mapper.readValue(new File("src/main/java/com/safety/safetyNet/config/data.json"), ListSafety.class);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
