package com.safety.safetyNet.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Persons {

    public  String firstName;
    public  String lastName;
    public String address;
    public String city;
    public String zip;
    public String phone;
    public String email;

}
