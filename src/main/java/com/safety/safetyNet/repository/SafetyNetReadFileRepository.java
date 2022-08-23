package com.safety.safetyNet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

/**
 * @author froid
 */
@Slf4j
@Repository
public class SafetyNetReadFileRepository {



    /**
     * Fonction pour lire le fichier de données.
     *
     * @return un object de type listSafety.
     * @param file pathFile
     */
    public ListSafety readfile(String file) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(file), ListSafety.class);
        } catch (IOException e) {
            log.error("Error :", e);
            throw new RuntimeException(e);
        }
    }
}
