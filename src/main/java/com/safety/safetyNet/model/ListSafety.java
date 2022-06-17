package com.safety.safetyNet.model;


import lombok.Data;

import java.util.ArrayList;

@Data
public class ListSafety {

   private ArrayList<Persons> persons;
   private ArrayList<Firestations> firestations;
   private ArrayList<MedicalRecords> medicalrecords;

}
