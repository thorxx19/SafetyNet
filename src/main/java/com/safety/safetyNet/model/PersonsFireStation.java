package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

/**
 * @author o.froidefond
 */
@Data
public class PersonsFireStation {

    private String lastName;
    private long age;
    private String email;
    private List<String> medications;
    private List<String> allergies;
}
