package com.safety.safetyNet.controller;

import com.safety.safetyNet.repository.SafetyNetRepository;
import com.safety.safetyNet.service.SafetyNetPersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetPersonController.class)
@Slf4j
class SafetyNetPersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyNetPersonService safetyNetPersonService;
    @MockBean
    private SafetyNetRepository safetyNetRepository;

    @Test
    public void testPostPerson() {
        try {
            mockMvc.perform(post("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\":\"Olivier\",\"lastName\":\"Froidefond\",\"address\":\"6 rue jacques prevert\",\"city\":\"culver\",\"zip\":\"97451\",\"phone\":\"841-874-5584\",\"email\":\"toto@toto.fr\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    public void testDeletePerson() {
        try {
            mockMvc.perform(delete("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\":\"Olivier\",\"lastName\":\"Froidefond\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    public void testPutPerson() {
        try {
            mockMvc.perform(put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\":\"Olivier\",\"lastName\":\"Froidefond\",\"address\":\"6 rue jacques prevert\",\"city\":\"culver\",\"zip\":\"97451\",\"phone\":\"841-874-5584\",\"email\":\"toto@toto.fr\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}