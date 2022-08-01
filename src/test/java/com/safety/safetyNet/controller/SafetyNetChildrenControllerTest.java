package com.safety.safetyNet.controller;

import com.safety.safetyNet.service.SafetyNetChildrenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class SafetyNetChildrenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test le end point /childAlert")
    public void testGetChildren() {
        try {
            mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

}