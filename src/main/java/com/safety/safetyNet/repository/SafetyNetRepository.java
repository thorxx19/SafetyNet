package com.safety.safetyNet.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;


@Slf4j
@Repository
public class SafetyNetRepository {

    public ListSafety getData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(PATH_FILE), ListSafety.class);
        } catch (Exception e) {
            log.error("error :", e);
        }
        return null;
    }

    public void writeData(ListSafety listSafety) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(PATH_FILE), listSafety);
        } catch (Exception e) {
            log.error("error :", e);
        }
    }


}
