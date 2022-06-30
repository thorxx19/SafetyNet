package com.safety.safetyNet.model;


import lombok.Data;

import java.util.List;

/**
 * @author o.froidefond
 */
@Data
public class PersonsMedical {

    private String lastName;
    private String phone;
    private long age;
    private List<String> medications;
    private List<String> allergies;
}
