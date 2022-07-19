package com.safety.safetyNet.model;

import lombok.Data;

import java.util.List;

/**
 * @author o.froidefond
 */
@Data
public class ResponseFireStationByNumber {

    private List<PersonsStation> personsStationList;
    private CountPeople countPeople;


}
