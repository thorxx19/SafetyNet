package com.safety.safetyNet.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ListSafety {

   private ArrayList<Persons> persons;
   private ArrayList<Firestations> firestations;
   private ArrayList<Medicalrecords> medicalrecords;

}
