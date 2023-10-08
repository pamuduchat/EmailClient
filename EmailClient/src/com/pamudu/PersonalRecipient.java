package com.pamudu;

import java.util.Calendar;

public class PersonalRecipient extends Recipient implements ToBeWished {
    private String nickname;
    private Calendar birthDay;

    public static String wish = "Hugs and love on your birthday. Pamudu";


    public PersonalRecipient (String name, String nickname, String email){
        setName(name);
        setNickname(nickname);
        setEmail(email);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
