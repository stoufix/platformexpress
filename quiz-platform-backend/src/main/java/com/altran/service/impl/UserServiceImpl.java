package com.altran.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.altran.config.PasswordGenerator;
import com.altran.model.LoginUser;
import com.altran.model.Privilege;
import com.altran.model.Question;
import com.altran.model.Quiz;
import com.altran.model.Role;
import com.altran.model.User;
import com.altran.repository.UserRepository;
import com.altran.service.MailService;
import com.altran.service.QuestionService;
import com.altran.service.QuizService;
import com.altran.service.UserService;
import static com.altran.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of user service
 * 
 * @author Maha.BSaid
 * @version 1.0
 *
 */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	private QuizService quizService;

	private QuestionService questionService;

	private BCryptPasswordEncoder bcryptEncoder;

	private MailService mailService;

	private PasswordGenerator passwordGenerator;

	/**
	 * Constructor of UserServiceImpl
	 * 
	 * @param userRepository the repository of user
	 * 
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Changes quiz service.
	 * 
	 * @param quizService quiz service.
	 */
	@Autowired
	public void setQuizService(QuizService quizService) {
		this.quizService = quizService;
	}

	/**
	 * Changes question service.
	 * 
	 * @param questionService question service.
	 */
	@Autowired
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	/**
	 * Changes bcryptEncoder service.
	 * 
	 * @param bcryptEncoder bcryptEncoder service.
	 */
	@Autowired
	public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
		this.bcryptEncoder = bcryptEncoder;
	}

	/**
	 * Changes mail service.
	 * 
	 * @param mailService mail service.
	 */
	@Autowired
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * Changes mail service.
	 * 
	 * @param mailService mail service.
	 */
	@Autowired
	public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

	/**
	 * Gets userDetails object by userName (needed by Spring security)
	 * 
	 * @param username: user name of the user
	 * @return userDetails object with the input userName
	 * @throws UsernameNotFoundException if there is no user with such userName
	 */
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user));
	}

	/**
	 * Gets the list of authority (needed by Spring security)
	 * 
	 * @return list of authority
	 */
	public List<GrantedAuthority> getAuthority(User user) {
		Role role = user.getRole();
		List<String> privileges = getPrivileges(role);
		return getGrantedAuthorities(privileges);
	}

	/**
	 * Gets the list of privileges from role
	 * 
	 * @return list of privileges
	 */
	private List<String> getPrivileges(Role role) {
		List<String> privileges = new ArrayList<>();
		Collection<Privilege> collection = new ArrayList<>();
		collection.addAll(role.getPrivileges());

		for (Privilege item : collection) {
			privileges.add(item.getTitle());
		}
		return privileges;
	}

	/**
	 * Gets the list of authorities from privileges
	 * 
	 * @return list of authorities
	 */
	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	/**
	 * Gets the list of all users sorted by firstName
	 * 
	 * @return list of all users sorted by firstName
	 */
	public List<User> findAll() {
		return userRepository.findAllByOrderByFirstNameAsc();
	}

	/**
	 * Gets the list of all users by page and sorted by firstName
	 * 
	 * @return list of all users by page and sorted by firstName
	 */
	public Page<User> findAllByPage(Authentication auth, Pageable pageable) {
		// return list of all users
		List<User> users = findAll();
		// gets authenticated user
		User authenticatedUser = getAuthenticatedUser(auth);
		// remove authenticated user from list of users
		List<User> result = users.stream().filter(user -> (authenticatedUser != user))
				.collect(Collectors.toCollection(ArrayList::new));
		// convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<User> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());
	}

	/**
	 * Gets one user by his id
	 * 
	 * @param id the id of the user
	 * @return user object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Gets a list of users according to a role
	 * 
	 * @param role the role of the users
	 * @return users list with the input role
	 */
	@Override
	public List<User> findByRole(String role) {
		List<User> users = userRepository.findAll();
		List<User> usersByRole = new ArrayList<>();
		for (User user : users) {
			if (user.getRole().getTitle().equals(role)) {
				usersByRole.add(user);
			}
		}
		return usersByRole;
	}

	/**
	 * Gets one user by his userName
	 * 
	 * @param userName the userName of the user
	 * @return user object with the same userName
	 * @throws UsernameNotFoundException if there is no user with such userName
	 */
	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return user;
	}

	/**
	 * Creates a new user
	 * 
	 * @param user the user to create
	 * @return the created user
	 * @throws IOException
	 * @throws MessagingException
	 * @throws AddressException
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public User create(User user) throws MessagingException, IOException {
		if (user.getId() != null && userRepository.existsById(user.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		// generate password automatically
		String decryptedPassword = passwordGenerator.generateCommonLangPassword();
		// encrypt password
		String password = bcryptEncoder.encode(decryptedPassword);
		// generate username automatically
		String email = user.getEmail();
		String username = email.replace("@altran.com", "");
		// set username
		user.setUsername(username);
		// Encode password
		user.setPassword(password);
		// set activated to true when the user created
		user.setActivated(true);
		// send email to user while creating him
		String subject = "Création compte PLateforme QCM";
		String newLigne = System.getProperty("line.separator");
		String message = "Votre compte a été crée avec succès " + newLigne + "Login : " + username + newLigne
				+ "mot de passe : " + decryptedPassword + newLigne
				+ "Veuillez changer votre mot de passe en cliquent sur ce lien" + newLigne + "http://localhost:4200/";

		// Creating a background Thread for sending email
		ExecutorService emailExecutor = Executors.newCachedThreadPool();
		emailExecutor.execute(() -> {
			try {
				mailService.sendmail(user, subject, message);
			} catch (MessagingException | IOException e) {
				logger.error(e);
			}
		});

		return userRepository.save(user);
	}

	/**
	 * Updates one user
	 * 
	 * @param id   the id of the updated user
	 * @param user the new user object with the new values
	 * @return the updated user
	 * @throws EntityNotFoundException if there is no entity with such ID in the
	 *                                 database
	 */
	@Override
	public User update(User user, Long id) {
		if (id != null && !userRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		user.setId(id);
		// Get the password from the old user object
		user.setPassword(findById(id).getPassword());
		return userRepository.save(user);
	}

	/**
	 * Deletes one user
	 * 
	 * @param id the id of the deleted user
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !userRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		userRepository.deleteById(id);
	}

	/**
	 * Changes account state of a user
	 * 
	 * @param id the of the updated user
	 * @return the updated user
	 */
	@Override
	public User changeAccountState(Long id) {
		User user = findById(id);
		if (user.isActivated())
			user.setActivated(false);
		else
			user.setActivated(true);
		return userRepository.save(user);
	}

	/**
	 * Gets the created question list for one user
	 * 
	 * @param id       the of the user
	 * @param pageable pagination information
	 * @return the list of created questions by this user returned by page
	 */
	@Override
	public Page<Question> getCreatedQuestions(Long id, Pageable pageable) {
		// return userRepository.findById(id).get().getCreatedQuestions();
		List<Question> createdQuestions = questionService.findAll();

		// select questions by id of user
		List<Question> createdQuestionsByIdUser = createdQuestions.stream()
				.filter(createdQuestion -> id.equals(createdQuestion.getCreatedBy().getId()))
				.collect(Collectors.toList());

		// convert list to page
		if (pageable.getOffset() >= createdQuestionsByIdUser.size()) {
			return Page.empty();
		}

		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > createdQuestionsByIdUser.size()
				? createdQuestionsByIdUser.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Question> subList = createdQuestionsByIdUser.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, createdQuestionsByIdUser.size());

	}

	/**
	 * Gets the created quizzes list for one user
	 * 
	 * @param id the of the user
	 * @return the list of created quizzes by this user returned by page
	 */
	@Override
	public Page<Quiz> getCreatedQuizzes(Long id, Pageable pageable) {

		// get all created quizzes
		List<Quiz> createdQuizzes = quizService.findAll();

		// select quizzes by id of user
		List<Quiz> createdQuizzesByIdUser = createdQuizzes.stream()
				.filter(createdQuiz -> id.equals(createdQuiz.getCreatedBy().getId())).collect(Collectors.toList());

		// convert list to page
		if (pageable.getOffset() >= createdQuizzesByIdUser.size()) {
			return Page.empty();
		}

		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > createdQuizzesByIdUser.size()
				? createdQuizzesByIdUser.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Quiz> subList = createdQuizzesByIdUser.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, createdQuizzesByIdUser.size());

	}

	/**
	 * Gets all userNames of all users
	 *
	 * @return list of userNames
	 */
	@Override
	public List<String> findAllUsernames() {
		List<User> users = userRepository.findAll();
		List<String> usernames = new ArrayList<>();
		if (!(users.isEmpty())) {
			for (User user : users) {
				usernames.add(user.getUsername());
			}
		}
		return usernames;
	}

	/**
	 * Gets the list of from all users
	 *
	 * @return list of mails
	 */
	@Override
	public List<String> findAllEmails() {
		List<User> users = userRepository.findAll();
		List<String> emails = new ArrayList<>();
		if (!(users.isEmpty())) {
			for (User user : users) {
				emails.add(user.getEmail());
			}
		}
		return emails;
	}

	/**
	 * Updates login information for user
	 * 
	 * @param id        the id of the user
	 * @param loginInfo the loginInfo object with the new userName and password
	 *                  values
	 * @return loginInfo object with the new login information
	 * @throws EntityNotFoundException if there is no entity with such ID in the
	 *                                 database.
	 */
	@Override
	public LoginUser changeLoginInfo(Long id, LoginUser loginInfo) {
		if (id != null && !userRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		User user = findById(id);
		user.setUsername(loginInfo.getUsername());
		user.setPassword(bcryptEncoder.encode(loginInfo.getPassword()));
		userRepository.save(user);
		return loginInfo;
	}

	/**
	 * Searches for users by one term
	 * 
	 * @param term the term to base search on it
	 * @return list of users contains the input term by page
	 */
	@Override
	public Page<User> simpleSearch(Authentication auth, String term, Pageable pageable) {
		User authUser = getAuthenticatedUser(auth);
		String username = authUser.getUsername();
		return userRepository.simpleSearch(username, term, pageable);
	}

	/**
	 * Gets authenticated user
	 * 
	 * @param Authentication object
	 * @return authenticated user
	 */
	public User getAuthenticatedUser(Authentication auth) {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) auth
				.getPrincipal();
		return findByUsername(principal.getUsername());
	}

}
