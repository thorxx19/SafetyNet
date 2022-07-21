package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePersonsByStationNumber {


    List<PersonsFireStation> personsByFireStations;
    private String address;


}
