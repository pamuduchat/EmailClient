package com.pamudu;


import java.util.Calendar;

public class OfficeFriendRecipient extends Recipient implements ToBeWished{
    private String designation;
    private Calendar birthDay;
    public static String wish = "Wish you a Happy Birthday. Pamudu";

    public OfficeFriendRecipient(String name,String email,String designation){
        setName(name);
        setEmail(email);
        setDesignation(designation);
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }



    public void setBirthDay(String birthDay) {
        this.birthDay = DateOperations.stringToDate(birthDay);

    }
    @Override
    public Calendar getBirthDay() {
        return birthDay;
    }

    @Override
    public String getWish() {
        return wish;
    }

    @Override
    public String getEmailAddress() {
        return getEmail();
    }
}
