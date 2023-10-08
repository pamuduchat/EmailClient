package finalall;

//200089G
//import libraries

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Email_Client {

    public static void main(String[] args) {
        com.pamudu.RecipientManager recipientManager = new com.pamudu.RecipientManager();
        recipientManager.loadRecipients();
        com.pamudu.EmailManager.retrieveStoredEmails();
        com.pamudu.EmailManager.sendWishesToday();
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

                            com.pamudu.EmailManager.sendMail(emailDetails[0], emailDetails[1], emailDetails[2]);
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
                            List<com.pamudu.ToBeWished> bornGivenDay = recipientManager.getRecipientsByBirthDay(input);
                            if(bornGivenDay.isEmpty()){
                                System.out.println("No recipient birthdays on "+input);
                            }
                            for (com.pamudu.ToBeWished recipient : bornGivenDay) {
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
                    List<com.pamudu.Email> emailsByDate = com.pamudu.EmailManager.getEmailsByDate(input);
                    try{
                        for (com.pamudu.Email email : emailsByDate) {
                            System.out.println("To: "+email.getTo() + " " +"Subject: "+ email.getSubject());
                        }
                    }catch (NullPointerException e){
                        //  No emails
                    }
                    break;
                case 5:
                    // code to print the number of recipient objects in the application
                    System.out.println("Number of recipients: "+ com.pamudu.Recipient.noOfRecipients);
                    break;
            }
        }

        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes

    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)





class DateOperations {

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




class Email implements Serializable {

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





class EmailManager {

    public static List<com.pamudu.Email> emailsList = new ArrayList<com.pamudu.Email>();

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
            com.pamudu.Email email = new com.pamudu.Email(emailAddress,subject, content,Calendar.getInstance());
            emailsList.add(email);
            storeEmails(emailsList);
            System.out.println("Done");

        } catch (MessagingException e) {
            System.out.println("Sending failed! Try again");
        }
    }

    // Sends a relevant birthday wish
    public static void sendBirthdayGreeting(com.pamudu.ToBeWished person){
        sendMail(person.getEmailAddress(),"Happy Birthday", person.getWish());
    }


    // Sends wishes to the recipients who have birthday on the present day
    public static void sendWishesToday(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar todayCal = Calendar.getInstance();
        String todayDateString = dateFormat.format(todayCal.getTime());
        List<com.pamudu.ToBeWished> todayBirthday = com.pamudu.RecipientManager.getRecipientsByBirthDay(todayDateString);

        List<com.pamudu.Email> sentMails = getEmailsByDate(todayDateString);
        List<String> alreadyWished = new ArrayList<String>();

        for (com.pamudu.Email email : sentMails) {
            String subject = email.getSubject();
            if (subject.equals("Happy Birthday")) {
                alreadyWished.add(email.getTo());
            }
        }

        for (com.pamudu.ToBeWished recipient : todayBirthday) {
            if (!(alreadyWished.contains(recipient.getEmailAddress()))) {
                sendBirthdayGreeting(recipient);
                System.out.println("sent wish");
            }
        }
    }


    private static void storeEmails(List<com.pamudu.Email> emailList){
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
            List<com.pamudu.Email> loadedEmailList = (List<com.pamudu.Email>) os.readObject();
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

    public static List<com.pamudu.Email> getEmailsByDate(String date){

        List<com.pamudu.Email> emailsGivenDay = new ArrayList<com.pamudu.Email>();

        Calendar givenDate = com.pamudu.DateOperations.stringToDate(date);

        try{
            for (com.pamudu.Email email : emailsList) {
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





class OfficeFriendRecipient extends com.pamudu.Recipient implements com.pamudu.ToBeWished {
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
        this.birthDay = com.pamudu.DateOperations.stringToDate(birthDay);

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



class OfficialRecipient extends com.pamudu.Recipient {
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




class PersonalRecipient extends com.pamudu.Recipient implements com.pamudu.ToBeWished {
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
        this.birthDay = com.pamudu.DateOperations.stringToDate(birthDay);
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


abstract class Recipient {
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





class RecipientManager {
    public static List<com.pamudu.Recipient> recipientsList = new ArrayList<com.pamudu.Recipient>();
    public static List<com.pamudu.ToBeWished> shouldWish = new ArrayList<com.pamudu.ToBeWished>();


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
                    com.pamudu.Recipient recipient = createRecipient(line);
                    recipientsList.add(recipient);
                    com.pamudu.Recipient.noOfRecipients += 1;
                    if (recipient instanceof com.pamudu.ToBeWished) {
                        shouldWish.add((com.pamudu.ToBeWished) recipient);
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
            com.pamudu.Recipient newRecipient = createRecipient(input);
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
            if (newRecipient instanceof com.pamudu.ToBeWished){
                shouldWish.add((com.pamudu.ToBeWished) newRecipient);
                if(isGivendayBday((com.pamudu.ToBeWished) newRecipient, com.pamudu.DateOperations.todayAsString())) {
                    com.pamudu.EmailManager.sendBirthdayGreeting((com.pamudu.ToBeWished) newRecipient);
                }
            }
            com.pamudu.Recipient.noOfRecipients += 1;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid input format");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    // create a new Recipient object using the input string
    public com.pamudu.Recipient createRecipient(String input){
        String[] inputArr = input.trim().split(":");
        String recipientType = inputArr[0];
        String[] detailsArr = inputArr[1].trim().split(",");

        switch (recipientType){
            case "Official" :
                com.pamudu.OfficialRecipient oRecipient = new com.pamudu.OfficialRecipient(detailsArr[0],detailsArr[1],detailsArr[2]);
                return oRecipient;
            case "Office_friend":
                com.pamudu.OfficeFriendRecipient ofriendRecipient = new com.pamudu.OfficeFriendRecipient(detailsArr[0],detailsArr[1],detailsArr[2]);
                ofriendRecipient.setBirthDay(detailsArr[3]);
                return ofriendRecipient;
            case "Personal":
                com.pamudu.PersonalRecipient persRecipient = new com.pamudu.PersonalRecipient(detailsArr[0],detailsArr[1],detailsArr[2]);
                persRecipient.setBirthDay(detailsArr[3]);
                return persRecipient;
        }
        return null;
    }


    // Used to return a list of recipients who have their birthday on a given day
    public static List<com.pamudu.ToBeWished> getRecipientsByBirthDay(String date){

        List<com.pamudu.ToBeWished> bornGivenDay = new ArrayList<com.pamudu.ToBeWished>();

        for(com.pamudu.ToBeWished recipient:shouldWish){
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
    public static Boolean isGivendayBday(com.pamudu.ToBeWished recipient, String date) throws ParseException {

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


interface ToBeWished {
    public  String getWish();
    public String getEmailAddress();

    public Calendar getBirthDay();

    public String getName();
}