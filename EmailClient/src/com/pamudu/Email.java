package com.pamudu;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Email implements Serializable {

    private String to;
    private String subject;
    private String content;
    private Calendar date;

    public Email(String to, String subject, String content,Calendar date){
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(date));
            this.date= cal;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
