package com.safety.safetyNet.model;


import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author o.froidefond
 */
@Data
public class Persons {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String address;
    private String city;
    @Min(5)
    private String zip;
    private String phone;
    @Email
    private String email;
}
