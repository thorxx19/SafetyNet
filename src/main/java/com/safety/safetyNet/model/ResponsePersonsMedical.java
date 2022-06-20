package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePersonsMedical {

    private List<PersonsMedical> personsMedicals;

    private Station station;



}
