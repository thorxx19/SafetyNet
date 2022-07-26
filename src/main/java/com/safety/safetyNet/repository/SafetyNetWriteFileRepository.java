package com.safety.safetyNet.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.configuration.SafetyNetConfiguration;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

/**
 * @author o.froidefond
 */

@Slf4j
@Repository
public class SafetyNetWriteFileRepository {
    @Autowired
    SafetyNetConfiguration safetyNetConfiguration;

    /**
     * Fonction pour écrire un objet de type listSafety dans le fichier.
     *
     * @param listSafety un object de type listSafety.
     */
    public void writeData(ListSafety listSafety) {
        String pathFile = safetyNetConfiguration.getPathFile();
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(pathFile), listSafety);
        } catch (IOException e) {
            log.error("error :", e);
            throw new RuntimeException("Error :", e);
        }
    }


}
