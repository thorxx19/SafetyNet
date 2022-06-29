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
public class ChildAlertIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getChildAlert() {
        try {
            mockMvc.perform(get("/childAlert?address=1509 Culver St")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$.children[0].firstName", is("Tenley")));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }


}
