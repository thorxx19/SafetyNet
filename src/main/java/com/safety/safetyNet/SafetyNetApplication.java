package com.safety.safetyNet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author o.froidefond
 */
@Slf4j
@SpringBootApplication
public class SafetyNetApplication {
    /**
     * fonction principal.
     *
     * @param args non utilisé
     */
    public static void main(String[] args) {
        SpringApplication.run(SafetyNetApplication.class, args);

    }

}
