package com.safety.safetyNet.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Persons {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

}
