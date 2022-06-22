package com.safety.safetyNet.integration;

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
public class FloodStationIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFloodStation(){
        try {
            mockMvc.perform(get("/flood/stations?stations=1")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$.personsFireStations[0].lastName",is("Duncan")));
        } catch (Exception e){
            log.error("error :", e);
        }
    }
}
