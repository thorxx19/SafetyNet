package com.safety.safetyNet.integration;


import com.safety.safetyNet.model.*;
import com.safety.safetyNet.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author o.froidefond
 */
@RestController
public class SafetyNetController {

    @Autowired
    SafetyNetService safetyNetService;


    /**
     * retourner une liste des personnes couvertes par la caserne de pompiers.
     *
     * @param stationNumber le numéro de station.
     * @return les habitants couverts par la station numéro
     */
    @GetMapping("/firestation")
    public ResponsePersonsStation getFireStationNumber(@RequestParam int stationNumber) {
        ResponsePersonsStation persons = safetyNetService.getAllPersonsWithStationNumber(stationNumber);
        return persons;
    }

    /**
     * retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     *
     * @param address adresse rechercher
     * @return la liste des enfant trouver a l'adresse
     */
    @GetMapping("/childAlert")
    public ResponsePersonsChildren getAddressChild(@RequestParam String address) {
        ResponsePersonsChildren children = safetyNetService.getChildrenThisAddress(address);
        return children;
    }

    /**
     * retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers.
     *
     * @param firestation n° de la station
     * @return liste de n° de tel des résident
     */
    @GetMapping("/phoneAlert")
    public ResponsePhone getNumberPhone(@RequestParam int firestation) {
        ResponsePhone phone = safetyNetService.getNumberPhoneThisFireStation(firestation);
        return phone;
    }

    /**
     * retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
     * de pompiers la desservant.
     *
     * @param address l'adresse rechercher
     * @return liste d'habitant
     */
    @GetMapping("/fire")
    public ResponsePersonsMedical getHabitantAtThisAdrdress(@RequestParam String address) {
        ResponsePersonsMedical persons = safetyNetService.getPersonsThisAddressPlusStationNumber(address);
        return persons;
    }

    /**
     * retourner une liste de tous les foyers desservis par la caserne
     *
     * @param stations n° de la caserne de pompier
     * @return liste de foyer
     */
    @GetMapping("/flood/stations")
    public ResponsePersonsFireStation getHomesAtThisStationNumber(@RequestParam int stations) {
        ResponsePersonsFireStation persons = safetyNetService.getHouseServeFireStation(stations);
        return persons;
    }

    /**
     * retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant.
     *
     * @param firstName le prénom rechercher
     * @param lastName  le nom rechercher
     * @return la fiche de la personne compléte
     */
    @GetMapping("/personInfo")
    public ResponsePersonInfo getMedicalRecordsOfThisPerson(@RequestParam String firstName, @RequestParam String lastName) {
        ResponsePersonInfo persons = safetyNetService.getPersonInfo(firstName, lastName);
        return persons;
    }

    /**
     * retourner les adresses mail de tous les habitants de la ville.
     *
     * @param city nom de la ville
     * @return les mail des habitants
     */
    @GetMapping("/communityEmail")
    public ResponseEmail getMailAllPersons(@RequestParam String city) {
        ResponseEmail listEmail = safetyNetService.getAllMail(city);
        return listEmail;
    }
}
