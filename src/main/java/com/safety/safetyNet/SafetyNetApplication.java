package com.safety.safetyNet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safety.safetyNet.model.ListSafety;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.service.SafetyNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.io.File;
import java.io.IOException;
@Slf4j
@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);

	}

}
