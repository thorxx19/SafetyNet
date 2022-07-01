package com.safety.safetyNet.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;

import static com.safety.safetyNet.constantes.SafetyNetConstantes.PATH_FILE;

/**
 * @author o.froidefond
 */

@Slf4j
@Repository
public class SafetyNetRepository {
    /**
     * Fonction pour lire le fichier de données.
     *
     * @return un object de type listSafety.
     */
    public ListSafety getData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(PATH_FILE), ListSafety.class);
        } catch (FileNotFoundException e) {
            log.error("Error :{}", e.getMessage());
        } catch (Exception ex){
            log.info("Error :{}", ex.getMessage());
        }
        return null;
    }

    /**
     * Fonction pour écrire un objet de type listSafety dans le fichier.
     *
     * @param listSafety un object de type listSafety.
     */
    public void writeData(ListSafety listSafety) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(PATH_FILE), listSafety);
        } catch (Exception e) {
            log.error("error :", e);
        }
    }


}
