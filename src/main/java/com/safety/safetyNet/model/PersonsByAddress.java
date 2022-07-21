package com.safety.safetyNet.model;


import lombok.Data;

import java.util.List;

/**
 * @author o.froidefond
 */
@Data
public class PersonsByAddress {
    Siblings siblings;
    List<PersonsAdult> adult;

}
