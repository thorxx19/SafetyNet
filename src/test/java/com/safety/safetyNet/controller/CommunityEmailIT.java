package com.safety.safetyNet.controller;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class CommunityEmailIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test la récupération de tous les mails pour une ville en paramètre")
    public void testGetCommunityEmail() {
        try {
            mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$.mail[0]", is("aly@imail.com")));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("Test la récupération de tous les mails pour une ville inconnue avec retour d'un objet vide")
    public void testGetCommunityEmailEmpty() {
        try {
            mockMvc.perform(get("/communityEmail?city=Culve")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$.mail", Matchers.empty()));
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
