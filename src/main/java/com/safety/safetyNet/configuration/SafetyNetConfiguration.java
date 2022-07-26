package com.safety.safetyNet.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "safetynet")
public class SafetyNetConfiguration {

    private String pathFile;

}
