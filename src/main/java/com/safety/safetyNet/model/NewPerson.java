package com.safety.safetyNet.model;

import lombok.Data;

@Data
public class NewPerson {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private String birthdate;
    private String medications;
    private String allergies;
}
