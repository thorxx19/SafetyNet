package com.safety.safetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.DeletePerson;
import com.safety.safetyNet.model.NewPerson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class PersonsIT {
    @Autowired
    private  MockMvc mockMvc;
    @Test
    @DisplayName("test l'intégration d'une nouvelle personne")
    public void testPerson1() {

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
    @Test
    @DisplayName("teste la mise a jour d'une fiche person")
    public void testPerson2(){
        NewPerson putPerson = new NewPerson();

        putPerson.setFirstName("Olivier");
        putPerson.setLastName("Froidefond");
        putPerson.setAddress("521 chemin d'enbiane");
        putPerson.setCity("Figeac");
        putPerson.setZip("46100");
        putPerson.setPhone("841-874-5584");
        putPerson.setEmail("toto@toto.fr");
        putPerson.setBirthdate("20/12/1981");
        putPerson.setMedications(null);
        putPerson.setAllergies("nillacilan");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            String requestJson = ow.writeValueAsString(putPerson);

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
    @DisplayName("Teste la suppression d'une personne grâce à son nom et prénom.")
    public void testPerson3(){
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
