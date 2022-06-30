package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

/**
 * @author o.froidefond
 */
@Data
public class ResponsePersonsMedical {

    private List<PersonsMedical> personsMedicals;

    private Station station;


}
