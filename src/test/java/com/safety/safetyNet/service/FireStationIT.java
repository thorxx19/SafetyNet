package com.safety.safetyNet.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.FireStations;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class FireStationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test le end point /firestation?stationNumber=3 et vérifie le résultat retourné.")
    public void testGetFireStation() {
        try {
            mockMvc.perform(get("/firestation?stationNumber=3")).andExpect(status()
                            .isOk())
                    .andExpect(jsonPath("$.personsStationList[0].firstName", is("John")))
                    .andExpect(jsonPath("$.personsStationList[0].lastName", is("Boyd")))
                    .andExpect(jsonPath("$.personsStationList[0].address", is("1509 Culver St")))
                    .andExpect(jsonPath("$.personsStationList[0].phone", is("841-874-6512")));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point /firestation?stationNumber=5 et vérifie le résultat retourné soit vide.")
    public void testGetFireStatioEmpty() {
        try {
            mockMvc.perform(get("/firestation?stationNumber=5")).andExpect(status()
                            .isOk())
                    .andExpect(jsonPath("$.personsStationList", Matchers.empty()));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point post /firestation.")
    public void testFireStation1() {
        FireStations fireStations = new FireStations();

        fireStations.setAddress("chemin d'enbiane");
        fireStations.setStation("2");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(fireStations);
            mockMvc.perform(post("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point put /firestation.")
    public void testFireStation2() {
        FireStations fireStations = new FireStations();

        fireStations.setAddress("chemin d'enbiane");
        fireStations.setStation("5");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(fireStations);
            mockMvc.perform(put("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point delete /firestation.")
    public void testFireStation3() {
        FireStations fireStations = new FireStations();

        fireStations.setAddress("chemin d'enbiane");
        fireStations.setStation("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(fireStations);
            mockMvc.perform(delete("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
