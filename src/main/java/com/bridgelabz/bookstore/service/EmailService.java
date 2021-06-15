package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailService {
	
	/**
	 * Email sending configuration
	 * @param to : To whom email is to be sent
	 * @param mailSubject : Subject of email
	 * @param mailBody : Body of email
	 * @throws AddressException : Exception Handling
	 * @throws MessagingException : Exception Handling
	 * @throws IOException : Exception Handling
	 */
	public void sendmail(String to,String mailSubject, String mailBody) throws AddressException, MessagingException, IOException {
		log.debug("Mail sending service.....");
		String username = System.getenv("mail_username");
		String password = System.getenv("mail_password");
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication(username, password);
			      }
			   });
		   try {
			   Message message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(username));
			   message.setRecipients(
			     Message.RecipientType.TO, InternetAddress.parse(to));
			   message.setSubject(mailSubject);
			   MimeBodyPart mimeBodyPart = new MimeBodyPart();
			   mimeBodyPart.setContent(mailBody, "text/html");

			   Multipart multipart = new MimeMultipart();
			   multipart.addBodyPart(mimeBodyPart);

			   message.setContent(multipart);
			   log.info("Mail sent");
			   Transport.send(message);
		   }
		   catch (Exception e) {
			   e.printStackTrace();
		   }
	}
}
