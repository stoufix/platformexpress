package com.altran.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.altran.util.Constants.EMAIL_ADMIN;
import static com.altran.util.Constants.PASS_ADMIN;

import org.springframework.stereotype.Service;

import com.altran.model.User;
import com.altran.service.MailService;

/**
 * Represents send mail notification
 * 
 * @author Dhouha.Bejaoui
 * @version 1.0
 */
@Service
public class MailServiceImpl implements MailService {

	/**
	 * Sends mail to user
	 * 
	 * @param user     receiver's mail
	 * @param subject  the subject of the mail
	 * @param messsage the message of the mail
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendmail(User user, String subject, String message) throws MessagingException, IOException {
		// properties of mail(protocol smtp, port 587, host smtp.gmail.com)
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// create session of admin's account
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_ADMIN, PASS_ADMIN);
			}
		});
		// create message to send
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(EMAIL_ADMIN, false));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
		msg.setSubject(subject);
		msg.setContent(message, "text/plain; charset=UTF-8");
		msg.setSentDate(new Date());
		Transport.send(msg);
	}

}
