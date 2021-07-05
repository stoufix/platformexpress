package com.altran.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.altran.model.User;
import com.altran.model.LoginUser;
import com.altran.model.Question;
import com.altran.model.Quiz;

/**
 * Represents the interface of user service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
public interface UserService {

	Page<User> findAllByPage(Authentication auth, Pageable pageable);

	User findById(Long id);

	User findByUsername(String username);

	List<User> findByRole(String role);

	List<User> findAll();

	List<String> findAllUsernames();

	List<String> findAllEmails();

	Page<User> simpleSearch(Authentication auth, String term, Pageable pageable);

	User create(User user) throws MessagingException, IOException;

	User update(User user, Long id);

	void delete(Long id);

	Page<Quiz> getCreatedQuizzes(Long id, Pageable pageable);

	LoginUser changeLoginInfo(Long id, LoginUser loginInfo);

	User changeAccountState(Long id);

	Page<Question> getCreatedQuestions(Long id, Pageable pageable);

	User getAuthenticatedUser(Authentication auth);

}