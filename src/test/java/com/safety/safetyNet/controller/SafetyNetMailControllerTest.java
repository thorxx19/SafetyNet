package com.safety.safetyNet.controller;

import com.safety.safetyNet.service.SafetyNetMailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@WebMvcTest(controllers = SafetyNetMailController.class)
@Slf4j
@ActiveProfiles("test")
class SafetyNetMailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SafetyNetMailService safetyNetMailService;


    @Test
    @DisplayName("test le end point /communityEmail?city=xxx")
    public void testGetMail() {
        try {
            mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}