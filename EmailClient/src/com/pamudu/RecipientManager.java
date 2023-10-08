package com.pamudu;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecipientManager {
    public static List<Recipient> recipientsList = new ArrayList<Recipient>();
    public static List<ToBeWished> shouldWish = new ArrayList<ToBeWished>();


    //  Load recipient details from the text file into the application
    public void loadRecipients (){
        try {
            String source = "clientList.txt";

            try{
                FileReader reader = new FileReader(source);
                reader.close();
            }
            catch (FileNotFoundException e){
                FileWriter writer = new FileWriter("clientList.txt",true);
                writer.close();

            }
            FileReader reader = new FileReader(source);
            BufferedReader bufreader = new BufferedReader(reader);
            String line = null;
            while ((line = bufreader.readLine()) != null) {

                // line = "" occurs
                try{
                    Recipient recipient = createRecipient(line);
                    recipientsList.add(recipient);
                    Recipient.noOfRecipients += 1;
                    if (recipient instanceof ToBeWished) {
                        shouldWish.add((ToBeWished) recipient);
                    }
                }
                catch (IndexOutOfBoundsException e){
                    continue;
                }
            }
            reader.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }


    //  Add recipient details in to text file
    public void addRecipient(String input) {
        try {
            Recipient newRecipient = createRecipient(input);
            recipientsList.add(newRecipient);
            try {
                FileWriter writer = new FileWriter("clientList.txt",true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(input);
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            // If new recipient's birthday is on the given day send a wish
            if (newRecipient instanceof ToBeWished){
                shouldWish.add((ToBeWished) newRecipient);
                if(isGivendayBday((ToBeWished) newRecipient,DateOperations.todayAsString())) {
                    EmailManager.sendBirthdayGreeting((ToBeWished) newRecipient);
                }
            }
            Recipient.noOfRecipients += 1;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid input format");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    // create a new Recipient object using the input string
    public Recipient createRecipient(String input){
        String[] inputArr = input.trim().split(":");
        String recipientType = inputArr[0];
        String[] detailsArr = inputArr[1].trim().split(",");

        switch (recipientType){
            case "Official" :
                OfficialRecipient oRecipient = new OfficialRecipient(detailsArr[0],detailsArr[1],detailsArr[2]);
                return oRecipient;
            case "Office_friend":
                OfficeFriendRecipient ofriendRecipient = new OfficeFriendRecipient(detailsArr[0],detailsArr[1],detailsArr[2]);
                ofriendRecipient.setBirthDay(detailsArr[3]);
                return ofriendRecipient;
            case "Personal":
                PersonalRecipient persRecipient = new PersonalRecipient(detailsArr[0],detailsArr[1],detailsArr[2]);
                persRecipient.setBirthDay(detailsArr[3]);
                return persRecipient;
        }
        return null;
    }


    // Used to return a list of recipients who have their birthday on a given day
    public static List<ToBeWished> getRecipientsByBirthDay(String date){

        List<ToBeWished> bornGivenDay = new ArrayList<ToBeWished>();

        for(ToBeWished recipient:shouldWish){
            Calendar bday = recipient.getBirthDay();
            try {
                if (isGivendayBday(recipient,date)){
                    bornGivenDay.add(recipient);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return bornGivenDay;
    }


    //  Check if recipient's birthday is on given day
    public static Boolean isGivendayBday(ToBeWished recipient,String date) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(date));
        Calendar bday = recipient.getBirthDay();

        if (bday.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && bday.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)){
            return true;
        }
        else{
            return false;
        }
    }

}
