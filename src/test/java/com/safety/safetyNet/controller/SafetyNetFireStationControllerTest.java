package com.safety.safetyNet.controller;


import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetFireStationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@WebMvcTest(controllers = SafetyNetFireStationController.class)
@Slf4j
public class SafetyNetFireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SafetyNetFireStationService safetyNetFireStationService;
    @MockBean
    private SafetyNetWriteFileRepository safetyNetWriteFileRepository;

    @Test
    @DisplayName("test de le end point /firestation?stationNumber=xxx")
    public void testGetFireStation() {
        try {
            mockMvc.perform(get("/firestation?stationNumber=3")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test de le end point /fire?address=xxx")
    public void testGetFire() {
        try {
            mockMvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test de le end point /flood/stations?stations=xxx")
    public void testGetFloodStation() {
        try {
            mockMvc.perform(get("/flood/stations?stations=3")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test de le end point post /firestation")
    public void testPostNewFireStation() {
        try {
            mockMvc.perform(post("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"address\" : \"chemin d'enbiane\",\"station\" : \"2\"}"))
                    .andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test de le end point delete /firestation")
    public void testDeleteFireStation() {
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
    @DisplayName("test de le end point put /firestation")
    public void testPutFireStation() {
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
