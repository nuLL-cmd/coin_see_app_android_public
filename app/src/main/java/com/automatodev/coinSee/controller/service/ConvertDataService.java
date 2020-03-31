package com.automatodev.coinSee.controller.service;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConvertDataService {
    private DateFormat dateFormat;
    private Locale locale;
    private NumberFormat numberFormat;

    public String convertDate(String value) {
        long timestamp = Long.parseLong(value) * 1000L;
        locale = new Locale("pt", "BR");
        dateFormat = new SimpleDateFormat("dd-MM-yyy HH:mm:ss", locale);
        return dateFormat.format(timestamp);

    }

    public String convertDayMonth(String value) {
        long timestamp = Long.parseLong(value)*1000L;
        locale = new Locale("pt", "BR");
        dateFormat = new SimpleDateFormat("dd-MM", locale);
        return dateFormat.format(timestamp);
    }

    public String convertDayString(String value){
        return value.substring(8,10)+"-"+value.substring(5,7);
    }
    public String convertDecimal(String value){
        double decimal = Double.parseDouble(value);
        locale = new Locale("pt", "BR");
        numberFormat = NumberFormat.getCurrencyInstance(locale);
       return numberFormat.format(decimal);
    }

    public String convertPhone(String value){
        return value = "("+ value.substring(0,2)+") "+value.substring(2,7)+"-"+value.substring(7,11);

    }
}
