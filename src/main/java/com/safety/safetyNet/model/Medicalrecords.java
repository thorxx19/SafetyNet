package com.safety.safetyNet.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Medicalrecords {

    public String firstName;
    public String lastName;
    public String birthdate;
    public ArrayList<String> medications;
    public ArrayList<String> allergies;

}
