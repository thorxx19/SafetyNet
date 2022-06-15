package com.safety.safetyNet.model;


import lombok.Data;

import java.util.ArrayList;

@Data
public class ListSafety {

    ArrayList<Persons> persons;
    ArrayList<Firestations> firestations;
    ArrayList<Medicalrecords> medicalrecords;



}
