package com.codecool.shop.service;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.*;


public class GmailMailer {

    public static void sendMail(String recipient, String name, String ordermessage){

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("webook.codecoolers@gmail.com", System.getenv("mailerPSWD"));
            }
        });


        Message message = new MimeMessage(session);
        try{
            message.setFrom(new InternetAddress("webook.codecoolers@gmail.com"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try {
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("WBook Team - Codecool shop project - Order Details (do not worry, not real :-))");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        String msg = "Hi "+ name +",<br/>" +
                "<br/>Thank you for shopping at WBook. Your order details:<br/>" +
                 ordermessage +
                "<br/>Kind regards,<br/>WBook Team; Ani, Máté, Tomi, Petra";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        try {
            mimeBodyPart.setContent(msg, "text/html");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Multipart multipart = new MimeMultipart();
        try{
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("mailing");
            //Feedback of sending email.
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
