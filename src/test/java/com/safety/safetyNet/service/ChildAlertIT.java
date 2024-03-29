package com.safety.safetyNet.service;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("test")
public class ChildAlertIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test le end point /childAlert?address=1509 Culver St et vérifie le résultat retourné.")
    public void getChildAlert() {
        try {
            mockMvc.perform(get("/childAlert?address=1509 Culver St")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$.[0].siblings.children[0].firstName", is("Tenley")));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point /childAlert?address=1509 Culver et vérifie que le résultat retouné soit vide.")
    public void getChildAlertEmpty() {
        try {
            mockMvc.perform(get("/childAlert?address=1509 Culver ")).andExpect(status()
                    .is4xxClientError()).andExpect(jsonPath("$", Matchers.empty()));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
