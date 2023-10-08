package com.pamudu;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;


public class EmailManager {

    public static List<Email> emailsList = new ArrayList<Email>();

    public static void sendMail(String emailAddress, String subject, String content) {

        final String username = "pamudulg@gmail.com";
        final String password = "gwgrifeicxavgjyr";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailAddress)
            );
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            Email email = new Email(emailAddress,subject, content,Calendar.getInstance());
            emailsList.add(email);
            storeEmails(emailsList);
            System.out.println("Done");

        } catch (MessagingException e) {
            System.out.println("Sending failed! Try again");
        }
    }

    // Sends a relevant birthday wish
    public static void sendBirthdayGreeting(ToBeWished person){
        sendMail(person.getEmailAddress(),"Happy Birthday", person.getWish());
    }


    // Sends wishes to the recipients who have birthday on the present day
    public static void sendWishesToday(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar todayCal = Calendar.getInstance();
        String todayDateString = dateFormat.format(todayCal.getTime());
        List<ToBeWished> todayBirthday = RecipientManager.getRecipientsByBirthDay(todayDateString);

        List<Email> sentMails = getEmailsByDate(todayDateString);
        List<String> alreadyWished = new ArrayList<String>();

        for (Email email : sentMails) {
            String subject = email.getSubject();
            if (subject.equals("Happy Birthday")) {
                alreadyWished.add(email.getTo());
            }
        }

        for (ToBeWished recipient : todayBirthday) {
            if (!(alreadyWished.contains(recipient.getEmailAddress()))) {
                sendBirthdayGreeting(recipient);
                System.out.println("sent wish");
            }
        }
    }


    private static void storeEmails(List<Email> emailList){
        String source = "emails.ser";
        try {
            FileOutputStream fileStream = new FileOutputStream(source);
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(emailList);
            os.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void retrieveStoredEmails(){
        String source = "emails.ser";
        try {
            FileInputStream fileStream = new FileInputStream(source);
            ObjectInputStream os = new ObjectInputStream(fileStream);
            List<Email> loadedEmailList = (List<Email>) os.readObject();
            emailsList.addAll(loadedEmailList);

        } catch (FileNotFoundException e) {
            try {
                FileOutputStream fileStream = new FileOutputStream(source);
                fileStream.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (InvalidClassException e){
            // No files serialized. Ignore this
        } catch (IOException e) {
            if (e instanceof EOFException){
                // Serialized file exists. No data inside
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Email> getEmailsByDate(String date){

        List<Email> emailsGivenDay = new ArrayList<Email>();

        Calendar givenDate = DateOperations.stringToDate(date);

        try{
            for (Email email : emailsList) {
                Calendar sentDate = email.getDate();
                Boolean sameYear = sentDate.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR);
                Boolean sameMonth = ((sentDate.get(Calendar.MONTH) == givenDate.get(Calendar.MONTH)));
                Boolean sameDay = (sentDate.get(Calendar.DAY_OF_MONTH) == givenDate.get(Calendar.DAY_OF_MONTH));

                if (sameYear && sameMonth && sameDay) {
                    emailsGivenDay.add(email);
                }
            }
        }catch (NullPointerException e){
            return null;
        }

        return emailsGivenDay;
    }

}