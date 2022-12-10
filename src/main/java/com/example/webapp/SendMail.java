package com.example.webapp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    public static void send(String from, String host, String port, String to, String subject, String text) {

        Properties props = new Properties();

        // Read properties file.

        props.put("mail.smtp.user", from);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            msg.setText(text, "UTF-8");
            msg.setSubject(subject,"UTF-8");
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    public static class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("pramataritest@gmail.com", "zffocnenjhioytwd");
        }
    }

    /*public static void send(String to, String sub, String msg, final String user, final String pass) {


        String host = "localhost";

        //Properties prop = new Properties();

        //prop.setProperty("mail.smtp.auth", "true");
        //prop.setProperty("mail.smtp.starttls.enable", "true");
        //prop.setProperty("mail.smtp.host", "smtp.gmail.com");
        //prop.setProperty("mail.smtp.port", "587");
        //prop.setProperty("mail.smtp.user", user);
        //prop.setProperty("mail.smtp.password", pass);



        //Session session = Session.getInstance(prop,
        //        new javax.mail.Authenticator() {
        //            protected PasswordAuthentication getPasswordAuthentication() {
        //                return new PasswordAuthentication(user, pass);
        //            }
        //        });
//
//
        ////Session session = Session.getDefaultInstance(prop);
        //try {
        //    MimeMessage message = new MimeMessage(session);
        //    message.setFrom(new InternetAddress(user));
        //    message.setReplyTo(InternetAddress.parse(user, false));
        //    message.setSubject(sub, "UTF-8");
        //    message.setText(msg, "UTF-8");
        //    message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
        //    Transport.send(message);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }*/
}
