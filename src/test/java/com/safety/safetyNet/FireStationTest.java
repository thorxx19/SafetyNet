package com.safety.safetyNet;


import com.safety.safetyNet.integration.SafetyNetController;
import com.safety.safetyNet.model.PersonsFireStation;
import com.safety.safetyNet.service.SafetyNetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetController.class)
@Slf4j
public class FireStationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyNetService safetyNetService;

    @Test
    public void testGetFireStation(){
        try {
            mockMvc.perform(get("/firestation?stationNumber=3")).andExpect(status().isOk());
        } catch (Exception e) {
           log.error("error :", e);
        }

    }


}
