package com.safety.safetyNet.model;


import lombok.Data;

import java.util.List;

@Data
public class ListSafety {

    private List<Persons> persons;
    private List<FireStations> firestations;
    private List<MedicalRecords> medicalrecords;

}
