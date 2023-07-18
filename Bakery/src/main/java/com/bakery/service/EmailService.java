package com.bakery.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	// Took code From my Springboot\Springbootstart-5\EMAIL_WEB_API...
	// Also adding dependency from https://mvnrepository.com/artifact/com.sun.mail/javax.mail for packages.....
	// Keep in mind to import only from javax.mail mostly here wherever the options prevail except Propeties(util)...
	
	// **
		public boolean sendEmail(String subject, String message, String to) {
			
			// Rest of the code...
					
					// *** Initially Considering That Email is not Sent... 
					boolean f = false; 
			
					// *** The OTP sending email id will be constant...
					String from = "amreshgarg2001@gmail.com";
			
					//Variable for gmail
					String host="smtp.gmail.com";
					
					//get the system properties
					Properties properties = System.getProperties();
					System.out.println("PROPERTIES "+properties);
					
					//setting important information to properties object
					
					//host set
					properties.put("mail.smtp.host", host);
					properties.put("mail.smtp.port","465");
					properties.put("mail.smtp.ssl.enable","true");
					properties.put("mail.smtp.auth","true");
					
					//Step 1: to get the session object..
					Session session=Session.getInstance(properties, new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {				
							// **
							return new PasswordAuthentication("amreshgarg2001@gmail.com", "ncchtnpgdnmyatxc");
						}				
						
					});
					
					session.setDebug(true);
					
					//Step 2 : compose the message [text,multi media]
					MimeMessage m = new MimeMessage(session);
					
					try {
					
					//from email
					m.setFrom(from);
					
					//adding recipient to message
					m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					
					//adding subject to message
					m.setSubject(subject);				
					
					//adding text to message
					
					// To set text to message...
					//m.setText(message);
					
					// To set text to message using html also...					
					m.setContent(message,"text/html");
					
					
					//send 
					
					//Step 3 : send the message using Transport class
					Transport.send(m);
					
					System.out.println("Sent success...................");
					
					// *** Here as our message is sent successfully, so setting our flag true...
					f = true;
					
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					// *** Returning our f now 
					return f;			
			
			// Rest of the code ends...
			
		}
	
	
	
	
}

