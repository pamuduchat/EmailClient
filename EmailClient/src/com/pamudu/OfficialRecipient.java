package com.pamudu;

public class OfficialRecipient extends Recipient {
    private String designation;

    public OfficialRecipient(){
    }

    public OfficialRecipient(String name, String email,String designation){
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
}
