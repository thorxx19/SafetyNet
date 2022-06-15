package com.safety.safetyNet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.service.SafetyNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
@Slf4j
@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {

			ListSafety data = mapper.readValue(new File("src/main/java/com/safety/safetyNet/config/data.json"),ListSafety.class);
			log.info(String.valueOf(data.getFirestations()));
			log.info(String.valueOf(data.getMedicalrecords()));
			log.info(String.valueOf(data.getPersons()));
			log.info(String.valueOf(data));


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
