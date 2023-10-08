package com.pamudu;

public abstract class Recipient {
    private String name;
    private String email;
    public static int noOfRecipients = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
