package com.safety.safetyNet.model;


import lombok.Data;

import java.util.List;

/**
 * @author o.froidefond
 */
@Data
public class MedicalRecords {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

}
