package com.safety.safetyNet.repository;

import com.safety.safetyNet.service.SafetyNetCalculatorAgeBirthdate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class SafetyNetReadFileRepositoryTest {


    @Autowired
    SafetyNetReadFileRepository safetyNetReadFileRepository;

    @Autowired
    SafetyNetCalculatorAgeBirthdate safetyNetCalculatorAgeBirthdate;

    @Test
    public void exceptionReadFile() {
        String fileError = "src/test/java/com/safety/safetyNet/config/dataTe.json";
        assertThrows(RuntimeException.class, () -> safetyNetReadFileRepository.readfile(fileError));

    }

    @Test
    public void exceptionCalculatorTest() {
        String age = "20-12-1981";
        assertThrows(RuntimeException.class, () -> safetyNetCalculatorAgeBirthdate.calculeDateBirthdate(age));
    }

}
