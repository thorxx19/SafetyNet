package com.safety.safetyNet.model;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.List;

/**
 * @author o.froidefond
 */
@Data
public class MedicalRecords {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Past
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

}
