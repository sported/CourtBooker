package com.utilities.util;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	private static String USER_NAME = "courts.booker@gmail.com"; 
//	private static String USER_NAME = "s.gurprit@gmail.com";// GMail user name (just the part before "@gmail.com")
//    private static String PASSWORD = "gpsingh123"; // GMail password
    private static String RECIPIENT = "s.gurprit@gmail.com";

	
	public static void send(Exception ex) throws Exception{
		System.out.println("Error in booking courts " + ex.getMessage());
		Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        String from = USER_NAME;
     
       
        Session session = Session.getDefaultInstance(props, null);
        String msgBody = ex.getMessage() +"\n"+ ex.toString();
        try{
        	Message message = new MimeMessage(session);
        	message.setFrom(new InternetAddress(from, "Court Booker"));
        	message.addRecipient(Message.RecipientType.TO,
                     new InternetAddress(RECIPIENT, "Gurprit"));
        	 
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            message.setSubject("Error in booking courts on " + dateFormat.format(date) );
            message.setText(msgBody);
          
            Transport.send(message);
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            throw ae;
        }
        catch (Exception  me) {
            me.printStackTrace();
            throw me;
        }
    
	}
	
	
}
