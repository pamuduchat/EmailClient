package com.pamudu;
//200089G
//import libraries

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Email_Client {

    public static void main(String[] args) {
        RecipientManager recipientManager = new RecipientManager();
        recipientManager.loadRecipients();
        EmailManager.retrieveStoredEmails();
        EmailManager.sendWishesToday();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application");

            int option;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                continue;
            }
            String input;
            switch (option) {
                case 1:
                    // input format - Official: nimal,nimal@gmail.com,ceo
                    // Use a single input to get all the details of a recipient
                    // code to add a new recipient
                    // store details in clientList.txt file
                    // Hint: use methods for reading and writing files
                    System.out.println("input format - Official: nimal,nimal@gmail.com,ceo");
                    scanner.nextLine();
                    if (scanner.hasNext()) {
                        input = scanner.nextLine();
                        recipientManager.addRecipient(input);
                    }
                    break;

                case 2:
                    // input format - email, subject, content
                    // code to send an email
                    System.out.println("input format - email, subject, content");
                    scanner.nextLine();
                    try{
                        if (scanner.hasNext()) {
                            input = scanner.nextLine();
                            String[] emailDetails = input.trim().split(",");

                            EmailManager.sendMail(emailDetails[0], emailDetails[1], emailDetails[2]);
                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid input format");
                    }
                    break;
                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print recipients who have birthdays on the given date
                    System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
                    scanner.nextLine();
                    try{
                    if (scanner.hasNext()) {
                        input = scanner.nextLine();
                        List<ToBeWished> bornGivenDay = recipientManager.getRecipientsByBirthDay(input);
                        if(bornGivenDay.isEmpty()){
                            System.out.println("No recipient birthdays on "+input);
                        }
                        for (ToBeWished recipient : bornGivenDay) {
                            System.out.println(recipient.getName());
                        }
                    }
                    }catch(RuntimeException e){
                        System.out.println("Invalid input");
                    }

                    break;

                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print the details of all the emails sent on the input date
                    System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    List<Email> emailsByDate = EmailManager.getEmailsByDate(input);
                    try{
                        for (Email email : emailsByDate) {
                            System.out.println("To: "+email.getTo() + " " +"Subject: "+ email.getSubject());
                        }
                    }catch (NullPointerException e){
                        //  No emails
                    }
                    break;
                case 5:
                    // code to print the number of recipient objects in the application
                    System.out.println("Number of recipients: "+Recipient.noOfRecipients);
                    break;
            }
        }

        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes

    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)