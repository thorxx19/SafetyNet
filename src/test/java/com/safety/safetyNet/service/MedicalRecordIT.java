package com.safety.safetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.MedicalRecords;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordIT {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("test le end point post /medicalRecord .")
    public void testMedicalRecord1() {
        MedicalRecords medicalRecords = new MedicalRecords();
        List<String> medicationList = new ArrayList<>();
        List<String> allergiesList = new ArrayList<>();
        medicationList.add("aznol:350mg");
        medicationList.add("hydrapermazol:100mg");

        allergiesList.add("nillacilan");


        medicalRecords.setFirstName("Olivier");
        medicalRecords.setLastName("Froidefond");
        medicalRecords.setBirthdate("04/06/1985");
        medicalRecords.setMedications(medicationList);
        medicalRecords.setAllergies(allergiesList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(medicalRecords);

            mockMvc.perform(post("/medicalRecord")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point put /medicalRecord .")
    public void testMedicalRecord2() {
        MedicalRecords medicalRecords = new MedicalRecords();
        List<String> medicationList = new ArrayList<>();
        List<String> allergiesList = new ArrayList<>();
        medicationList.add("aznol:350mg");


        allergiesList.add("nillacilan");


        medicalRecords.setFirstName("Olivier");
        medicalRecords.setLastName("Froidefond");
        medicalRecords.setBirthdate("20/12/1981");
        medicalRecords.setMedications(medicationList);
        medicalRecords.setAllergies(allergiesList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(medicalRecords);

            mockMvc.perform(put("/medicalRecord")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point delete /medicalRecord .")
    public void testMedicalRecord3() {
        MedicalRecords medicalRecords = new MedicalRecords();
        List<String> medicationList = new ArrayList<>();
        List<String> allergiesList = new ArrayList<>();

        medicalRecords.setFirstName("Olivier");
        medicalRecords.setLastName("Froidefond");
        medicalRecords.setBirthdate("20/12/1981");
        medicalRecords.setMedications(medicationList);
        medicalRecords.setAllergies(allergiesList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(medicalRecords);

            mockMvc.perform(delete("/medicalRecord")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
