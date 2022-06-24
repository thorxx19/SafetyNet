package com.safety.safetyNet.service;

import com.safety.safetyNet.controller.SafetyNetPersonController;
import com.safety.safetyNet.repository.SafetyNetRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
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
class SafetyNetPersonServiceTest {

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
                            .content("{\"firstName\":\"Olivier\",\"lastName\":\"Froidefond\",\"address\":\"6 rue jacques prevert\",\"city\":\"culver\",\"zip\":\"97451\",\"phone\":\"841-874-5584\",\"email\":\"toto@toto.fr\",\"birthdate\":\"20/12/1981\",\"medications\":\"dodoxadin:30mg\",\"allergies\":\"nillacilan\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
    @Test
    public void testDeletePerson(){
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
    @Disabled
    //todo a faire avec la méthode
    public void testPutPerson(){
        try {
            mockMvc.perform(put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\":\"Olivier\",\"lastName\":\"Froidefond\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}