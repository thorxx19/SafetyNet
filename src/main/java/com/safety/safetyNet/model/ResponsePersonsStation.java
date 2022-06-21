package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePersonsStation {

    private List<PersonsStation> personsStationList;
    private CountPeople countPeople;


}
