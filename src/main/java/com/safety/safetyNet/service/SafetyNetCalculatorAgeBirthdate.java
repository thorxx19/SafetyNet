package com.safety.safetyNet.service;

import com.safety.safetyNet.model.MedicalRecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class SafetyNetCalculatorAgeBirthdate {

    /**
     * method pour calculer l'age
     *
     * @param date object de type MedicalRecords
     * @return l'age calculer
     */
    public long calculeDateBirthdate(String date) {


            Calendar today = Calendar.getInstance();
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy");

        Date birth = null;
        try {
            birth = dateTimeFormatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date todayParse = today.getTime();
            long result = todayParse.getTime() - birth.getTime();
            TimeUnit time = TimeUnit.DAYS;
            long resultDay = time.convert(result, TimeUnit.MILLISECONDS);


        return resultDay / 365;
    }


}
