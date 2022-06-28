package com.safety.safetyNet.controller;

import com.safety.safetyNet.service.SafetyNetMedicalRecordController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetMedicalRecordController.class)
@Slf4j
class SafetyNetMedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyNetMedicalRecordService safetyNetMedicalRecordService;

    //todo a finir
    @Test
    @Disabled
    public void testGetMedicalRecord() {
        try {
            mockMvc.perform(get("")).andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}