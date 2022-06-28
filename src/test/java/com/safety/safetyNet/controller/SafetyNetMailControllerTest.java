package com.safety.safetyNet.controller;

import com.safety.safetyNet.service.SafetyNetMailController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetMailController.class)
@Slf4j
class SafetyNetMailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SafetyNetMailService safetyNetMailService;


    @Test
    public void testGetMail() {
        try {
            mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}