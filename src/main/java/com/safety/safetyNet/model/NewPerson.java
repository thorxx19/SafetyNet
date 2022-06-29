package com.safety.safetyNet.model;

import lombok.Data;

@Data
public class NewPerson {
    private Persons person;
    private MedicalRecords medical;
}
