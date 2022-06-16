package com.safety.safetyNet.model;


import lombok.Data;


import java.util.List;

@Data
public class Medicalrecords {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

}
