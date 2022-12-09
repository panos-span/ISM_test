package com.example.webapp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public static void send(String to,String sub,String msg,final String user,final String pass){


        String host="localhost";
        Properties prop =System.getProperties();
        prop.setProperty("mail.smtp.host",host);
        Session session=Session.getDefaultInstance(prop);
        try {
            MimeMessage message= new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setReplyTo(InternetAddress.parse(user,false));
            message.setSubject(sub,"UTF-8");
            message.setText(msg,"UTF-8");
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
