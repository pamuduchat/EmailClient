package com.pamudu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DateOperations {

    public static String dateToString(Calendar cal){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String str = dateFormat.format(cal.getTime());
        return str;

    }

    public static Calendar stringToDate(String date){
        Calendar convertedDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(date));
            convertedDate = cal;

        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        return convertedDate;
    }

    public static String todayAsString(){
        Calendar calendar = Calendar.getInstance();
        String today = dateToString(calendar);
        return today;
    }

}
