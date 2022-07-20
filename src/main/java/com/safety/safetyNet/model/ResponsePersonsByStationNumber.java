package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePersonsByStationNumber {


    private String address;
    List<PersonsFireStation> personsByFireStations;


}
