package com.safety.safetyNet.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author o.froidefond
 */
@Data
public class DeletePerson {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
