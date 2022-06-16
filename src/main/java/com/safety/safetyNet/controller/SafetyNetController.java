package com.safety.safetyNet.controller;



import com.safety.safetyNet.model.Email;
import com.safety.safetyNet.model.MessageError;
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
    public Object getFireStationNumber(@RequestParam int stationNumber){
        MessageError message = new MessageError();
        message.setMessage("sation n° "+stationNumber+ " non implémenter");
        message.setError("error numéro de caserne de pompier");
        return message;
    }

    /**
     * retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * @param address adresse rechercher
     * @return la liste des enfant trouver a l'adresse
     */
    @GetMapping("/childAlert")
    public Object getAddressChild(@RequestParam String address){
        MessageError message = new MessageError();
        message.setMessage("l'adresse de cette enfant n'est pas trouver");
        message.setError("error d'adresse");
        return message;
    }

    /**
     * retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers.
     * @param firestation n° de la station
     * @return liste de n° de tel des résident
     */
    @GetMapping("/phoneAlert")
    public Object getNumberPhone(@RequestParam int firestation){
        MessageError message = new MessageError();
        message.setMessage("pas d'habitant enregistrée pour la staion "+firestation+"");
        message.setError("error pas de numéro de téléphone ");
        return message;
    }

    /**
     *  retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
     * de pompiers la desservant.
     * @param address l'adresse rechercher
     * @return liste d'habitant
     */
    @GetMapping("/fire")
    public Object getHabitantAtThisAdrdress(@RequestParam String address){
        MessageError message = new MessageError();
        message.setMessage("il n'y pas d'habitant a l'adresse "+ address +"");
        message.setError("error d'adresse");
        return message;
    }

    /**
     * retourner une liste de tous les foyers desservis par la caserne
     * @param stations n° de la caserne de pompier
     * @return liste de foyer
     */
    @GetMapping("/flood/stations")
    public Object getHomesAtThisStationNumber(@RequestParam int stations){
        MessageError message = new MessageError();
        message.setMessage("pas d'habitant pour cette caserne de pompier N° "+ stations +"");
        message.setError("error number stations");
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
    public Object getMedicalRecordsOfThisPerson(@RequestParam String firstName,@RequestParam String lastName){
        MessageError message = new MessageError();
        message.setMessage("pas de correspondence pour "+ lastName +" "+ firstName +"");
        message.setError("error firstName or lastName");
      return message;
    }

    /**
     * retourner les adresses mail de tous les habitants de la ville.
     * @param city nom de la ville
     * @return les mail des habitants
     */
    @GetMapping("/communityEmail")
    public Object getMailAllPersons(@RequestParam String city){

        List<Email> listEmail = safetyNetService.getAllMail(city);
        if (listEmail == null){
            MessageError message = new MessageError();
            message.setMessage("il y a pas donnée pour la ville de "+ city + "");
            message.setError("error name of city");
            return message;
        }else{
            return listEmail;
        }
    }
}
