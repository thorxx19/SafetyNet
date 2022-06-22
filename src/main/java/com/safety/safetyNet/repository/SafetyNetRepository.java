package com.safety.safetyNet.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import static com.safety.safetyNet.constantes.SafetyNetConstantes.*;



@Slf4j
@Repository
public class SafetyNetRepository {

    public ListSafety getData() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            return mapper.readValue(new File(PATH_FILE), ListSafety.class);
        } catch (Exception e) {
            log.error("error :", e);
        }
        return null;
    }



}
