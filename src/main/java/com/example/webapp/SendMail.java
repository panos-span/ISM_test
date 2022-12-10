package com.example.webapp;

import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    public static void send(String from, String host, String port, ArrayList<Customer> customers /*String to*/, String subject, String text) {

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
            msg.setSubject(subject, "UTF-8");
            msg.setFrom(new InternetAddress(from));
            for (Customer cust : customers) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(cust.getEmail()));
            }
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    public static class SMTPAuthenticator extends javax.mail.Authenticator {
        private final String user = "pramataritest@gmail.com";
        private final String pass = "zffocnenjhioytwd";

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pass);
        }
    }

}
