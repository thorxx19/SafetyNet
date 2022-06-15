package com.safety.safetyNet.model;


import lombok.Data;


import java.util.List;

@Data
public class Medicalrecords {

    public String firstName;
    public String lastName;
    public String birthdate;
    public List<String> medications;
    public List<String> allergies;

}
