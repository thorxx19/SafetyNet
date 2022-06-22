package com.safety.safetyNet.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PersonInfoIT {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testGetPersonInfo(){
        try {
            mockMvc.perform(get("/personInfo?firstName=Jonanathan&lastName=Marrack")).andExpect(status()
                    .isOk()).andExpect(jsonPath("$.personInfo.lastName",is("Marrack")));
        } catch (Exception e){
            log.error("error :", e);
        }
    }
}
