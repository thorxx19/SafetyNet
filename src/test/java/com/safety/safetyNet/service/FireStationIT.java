package com.safety.safetyNet.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class FireStationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
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

}
