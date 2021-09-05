package com.express.service;

import java.io.IOException;

import javax.mail.MessagingException;
import com.express.model.User;

/**
 * Represents the interface of mail service
 * 
 Dhouha.Bejaoui
 * @version 1.0
 */
public interface MailService {

	void sendmail(User user, String subject, String message) throws MessagingException, IOException;

}
