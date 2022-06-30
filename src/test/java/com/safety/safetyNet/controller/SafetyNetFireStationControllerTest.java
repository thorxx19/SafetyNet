package com.safety.safetyNet.controller;


import com.safety.safetyNet.repository.SafetyNetRepository;
import com.safety.safetyNet.service.SafetyNetFireStationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetFireStationController.class)
@Slf4j
public class SafetyNetFireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyNetFireStationService safetyNetFireStationService;
    @MockBean
    private SafetyNetRepository safetyNetRepository;

    @Test
    public void testGetFireStation() {
        try {
            mockMvc.perform(get("/firestation?stationNumber=3")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    public void testGetFire() {
        try {
            mockMvc.perform(get("http://localhost:9000/fire?address=1509 Culver St")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    public void testGetFloodStation() {
        try {
            mockMvc.perform(get("http://localhost:9000/flood/stations?stations=3")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    public void testPostNewFireStation(){
        try {
           mockMvc.perform(post("/firestation")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content("{\"address\" : \"chemin d'enbiane\",\"station\" : \"2\"}"))
                   .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
    @Test
    public void testDeleteFireStation(){
        try {
            mockMvc.perform(delete("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"address\" : \"chemin d'enbiane\",\"station\" : \"\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
    @Test
    public void testPutFireStation(){
        try {
            mockMvc.perform(put("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"address\" : \"chemin d'enbiane\",\"station\" : \"5\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
