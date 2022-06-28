package com.safety.safetyNet.controller;

import com.safety.safetyNet.service.SafetyNetPhoneService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetPhoneController.class)
@Slf4j
class SafetyNetPhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyNetPhoneService safetyNetPhoneService;

    @Test
    public void testGetFireStation() {
        try {
            mockMvc.perform(get("http://localhost:9000/phoneAlert?firestation=4")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}