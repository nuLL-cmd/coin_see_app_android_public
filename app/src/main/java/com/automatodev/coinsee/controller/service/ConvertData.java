package com.automatodev.coinsee.controller.service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConvertData {
    private SimpleDateFormat dateFormat;
    private Locale locale;
    private NumberFormat numberFormat;

    public String convertDate(String value) {
        long timestamp = Long.parseLong(value);
        locale = new Locale("pt", "BR");
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", locale);
        return dateFormat.format(timestamp);
    }

    public String convertHour(String value) {
        long timestamp = Long.parseLong(value);
        locale = new Locale("pt", "BR");
        dateFormat = new SimpleDateFormat("HH:mm:ss", locale);
        return dateFormat.format(timestamp);
    }
    public String convertDecimal(String value){
        double decimal = Double.parseDouble(value);
        locale = new Locale("pt", "BR");
        numberFormat = NumberFormat.getCurrencyInstance(locale);
       return numberFormat.format(decimal);
    }

    public String convertPercent(String value){
        double percent = Double.parseDouble(value);
        locale = new Locale("pt","BR");
        numberFormat = NumberFormat.getPercentInstance(locale);
        return numberFormat.format(percent);
    }
}
