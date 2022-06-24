package com.safety.safetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.NewPerson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class PersonsIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPostPerson() {

        NewPerson newPerson = new NewPerson();

        newPerson.setFirstName("Olivier");
        newPerson.setLastName("Froidefond");
        newPerson.setAddress("6 rue jacques prevert");
        newPerson.setCity("culver");
        newPerson.setZip("97451");
        newPerson.setPhone("841-874-5584");
        newPerson.setEmail("toto@toto.fr");
        newPerson.setBirthdate("20/12/1981");
        newPerson.setMedications("dodoxadin:30mg");
        newPerson.setAllergies("nillacilan");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(newPerson);

            mockMvc.perform(post("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
