package com.safety.safetyNet.service;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class FloodStationIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test le end point /flood/stations?stations=1 et vérifie le résultat retourné.")
    public void testGetFloodStation() {
        try {
            mockMvc.perform(get("/flood/stations?stations=1")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$.[0].personsByFireStations[0].lastName", is("Duncan")));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point /flood/stations?stations=5 et vérifie le résultat retourné soit vide.")
    public void testGetFloodStationEmpty() {
        try {
            mockMvc.perform(get("/flood/stations?stations=5")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$", Matchers.empty()));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
