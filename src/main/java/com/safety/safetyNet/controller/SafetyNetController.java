package com.safety.safetyNet.controller;



import com.safety.safetyNet.model.Email;
import com.safety.safetyNet.model.Persons;
import com.safety.safetyNet.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import static com.safety.safetyNet.constantes.SafetyNetConstantes.*;

/**
 * @author o.froidefond
 */
@RestController
public class SafetyNetController {

    @Autowired
    SafetyNetService safetyNetService;



    /**
     * retourner une liste des personnes couvertes par la caserne de pompiers.
     * @param stationNumber le numéro de station.
     * @return les habitants couverts par la station numéro
     */
    @GetMapping("/firestation")
    public String getFireStationNumber(@RequestParam int stationNumber){
        String message = "{\"message\": \"sation n° "+stationNumber+"\" non implémenter\"}";
        return message;
    }

    /**
     * retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * @param address adresse rechercher
     * @return la liste des enfant trouver a l'adresse
     */
    @GetMapping("/childAlert")
    public String getAddressChild(@RequestParam String address){
        String message = "{\"message\": \"l'adresse de cette enfant et pas trouver\"}";
        return message;
    }

    /**
     *  retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers.
     * @param firestation n° de la station
     * @return liste de n° de tel des résident
     */
    @GetMapping("/phoneAlert")
    public String getNumberPhone(@RequestParam int firestation){
        String message = "{\"message\": \"pas de N° de tel a cette station\"}";
        return message;
    }

    /**
     *  retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
     * de pompiers la desservant.
     * @param address l'adresse rechercher
     * @return liste d'habitant
     */
    @GetMapping("/fire")
    public String getHabitantAtThisAdrdress(@RequestParam String address){
        String message = "{\"message\": \"pas d'habitant a cette adresse\"}";
        return message;
    }

    /**
     * retourner une liste de tous les foyers desservis par la caserne
     * @param stations n° de la caserne de pompier
     * @return liste de foyer
     */
    @GetMapping("/flood/stations")
    public String getHomesAtThisStationNumber(@RequestParam int stations){
        String message = "{\"message\": \"pas d'habitant pour cette caserne de pompier\"}";
        return message;
    }

    /**
     * retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant.
     * @param firstName le prénom rechercher
     * @param lastName le nom rechercher
     * @return la fiche de la personne compléte
     */
    @GetMapping("/personInfo")
    public String getMedicalRecordsOfThisPerson(@RequestParam String firstName,@RequestParam String lastName){
        String message = "{\"message\": \"profil de l'habitant : " + firstName + " " + lastName + "\"}";
      return message;
    }

    /**
     * retourner les adresses mail de tous les habitants de la ville.
     *
     * @param city nom de la ville
     * @return les mail des habitants
     */
    @GetMapping("/communityEmail")
    public Object getMailAllPersons(@RequestParam String city){
       ArrayList<Persons> dataPersons = safetyNetService.getAllPersons();

        List<Email> listEmail = new ArrayList<>();

   if (dataPersons != null) {
    for (Persons data : dataPersons) {

        if (city.equals(data.getCity())){
            Email mail = new Email();
            mail.setEmail(data.getEmail());
            listEmail.add(mail);
        }
    }

         }else{
               return null;
           }
                return listEmail;
            }
}
