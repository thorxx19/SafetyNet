package com.safety.safetyNet.controller;

import com.safety.safetyNet.service.SafetyNetChildrenController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetChildrenController.class)
@Slf4j
class SafetyNetChildrenControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SafetyNetChildrenService safetyNetChildrenService;

    @Test
    public void testGetChildren() {
        try {
            mockMvc.perform(get("http://localhost:9000/childAlert?address=1509 Culver St"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

}