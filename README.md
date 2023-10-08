# Java Command-Line Email Client

This Java-based Command-Line Email Client is designed to simplify email management from the command line. It offers the following core features:

## Features

1. **Adding a New Recipient**
   - Easily add a new recipient to your email client.
   - Input format: `recipientType:name,email,designation`
   - Example: `Personal:John Doe,john@example.com,Friend`

2. **Sending an Email**
   - Compose and send emails directly from the command line.
   - Input format: `emailAddress,subject,content`
   - Example: `recipient@example.com,Hello,This is the email content`

3. **Printing Recipients with Birthdays**
   - Print recipients who have birthdays on a given date.
   - Input format: `yyyy/MM/dd`
   - Example: `2023/10/08`

4. **Printing Details of Sent Emails**
   - Print the details of all emails sent on a specific date.
   - Input format: `yyyy/MM/dd`
   - Example: `2023/10/08`

5. **Printing the Number of Recipient Objects**
   - View the number of recipient objects in the application.

## Usage

To interact with the email client, enter the corresponding option number:

1. **Adding a New Recipient**
   - Use option `1` to add a new recipient. Follow the input format to provide recipient details.

2. **Sending an Email**
   - Use option `2` to send an email. Follow the input format to specify the email recipient, subject, and content.

3. **Printing Recipients with Birthdays**
   - Choose option `3` to print recipients who have birthdays on a specific date. Enter the date in the format `yyyy/MM/dd`.

4. **Printing Details of Sent Emails**
   - Select option `4` to print the details of all emails sent on a particular date. Input the date in the format `yyyy/MM/dd`.

5. **Printing the Number of Recipient Objects**
   - Use option `5` to print the number of recipient objects currently in the application.
