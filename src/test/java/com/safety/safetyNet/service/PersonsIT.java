package com.safety.safetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.model.Persons;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author o.froidefond
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class PersonsIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test le end point post /person.")
    public void testPerson1() {

        Persons person = new Persons();

        person.setFirstName("Olivier");
        person.setLastName("Froidefond");
        person.setAddress("6 rue jacques prevert");
        person.setCity("culver");
        person.setZip("97451");
        person.setPhone("841-874-5584");
        person.setEmail("toto@toto.fr");


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(person);

            mockMvc.perform(post("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point put /person.")
    public void testPerson2() {

        Persons person = new Persons();

        person.setFirstName("Olivier");
        person.setLastName("Froidefond");
        person.setAddress("521 chemin d'enbiane");
        person.setCity("Figeac");
        person.setZip("46100");
        person.setPhone("841-874-5584");
        person.setEmail("toto@toto.fr");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(person);

            mockMvc.perform(put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }

    @Test
    @DisplayName("test le end point delete /person.")
    public void testPerson3() {
        DeletePerson deletePerson = new DeletePerson();

        deletePerson.setFirstName("Olivier");
        deletePerson.setLastName("Froidefond");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(deletePerson);
            mockMvc.perform(delete("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.error("error :", e);
        }
    }
}
