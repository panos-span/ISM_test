package com.example.webapp;


import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

    public static void main(String[] args) throws SQLException {
        String d_email = "pramataritest@gmail.com",
                d_password = "zffocnenjhioytwd",
                d_host = "smtp.gmail.com",
                d_port = "465",
                m_to = "",
                m_subject = "Testing",
                m_text = "Hey, this is the testing email.";
        send(d_email, d_host, d_port, m_to, m_subject, m_text);
    }


    // Those are the values that have the email information
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

            msg.setText(text);
            msg.setSubject(subject);
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

    private static String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }
}
