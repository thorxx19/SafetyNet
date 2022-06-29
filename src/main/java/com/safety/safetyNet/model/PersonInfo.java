package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

@Data
public class PersonInfo {

    private String lastName;
    private String address;
    private long age;
    private String email;
    private List<String> medications;
    private List<String> allergies;


}
