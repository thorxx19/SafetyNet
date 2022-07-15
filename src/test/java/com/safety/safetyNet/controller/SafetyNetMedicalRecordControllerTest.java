package com.safety.safetyNet.controller;

import com.safety.safetyNet.repository.SafetyNetWriteFileRepository;
import com.safety.safetyNet.service.SafetyNetMedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@WebMvcTest(controllers = SafetyNetMedicalRecordController.class)
@Slf4j
class SafetyNetMedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SafetyNetMedicalRecordService safetyNetMedicalRecordService;
    @MockBean
    private SafetyNetWriteFileRepository safetyNetWriteFileRepository;


    @Test
    @DisplayName("test le end point post /medicalRecord")
    public void testPostMedicalRecord() {
        try {
            mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\" : \"Olivier\",\"lastName\" : \"Froidefond\",\"birthdate\" : \"03/06/1984\",\"medications\" : [ \"aznol:350mg\", \"hydrapermazol:100mg\" ],\"allergies\" : [ \"nillacilan\" ]}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point put /medicalRecord")
    public void testPutMedicalRecord() {
        try {
            mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\" : \"Olivier\",\"lastName\" : \"Froidefond\",\"birthdate\" : \"20/12/1981\",\"medications\" : [ \"aznol:350mg\" ],\"allergies\" : [ \"nillacilan\" ]}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point delete /medicalRecord")
    public void testDeleteMedicalRecord() {
        try {
            mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\" : \"Olivier\",\"lastName\" : \"Froidefond\",\"birthdate\" : \"20/12/1981\",\"medications\" : [],\"allergies\" : []}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}