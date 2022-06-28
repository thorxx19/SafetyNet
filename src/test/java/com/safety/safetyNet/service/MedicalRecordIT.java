package com.safety.safetyNet.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordIT {
    @Autowired
    private MockMvc mockMvc;

    //todo a finir
    @Test
    @DisplayName("test l'intégration d'un dossier médical")
    @Disabled
    public void testMedicalRecord1(){

    }


}
