package com.express.util;

/**
 * Represents constants to use in application
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
public class Constants {

	/**
	 * Private constructor to hide the implicit public one. (SonarQube message)
	 */
	private Constants() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Spring security constants
	 */
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = (long) 5 * 60 * 60;

	public static final String SIGNING_KEY = "azerty100";

	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String HEADER_STRING = "Authorization";

	public static final String AUTHORITIES_KEY = "scopes";

	/**
	 * Mail's address and password
	 */
	public static final String EMAIL_ADMIN = "testdhouha@gmail.com";

	public static final String PASS_ADMIN = "package@@2018";

	/**
	 * Error, message and object to return
	 */
	public static final String ERROR = "error";

	public static final String MESSAGE = "message";

	public static final String OBJECT = "object";

	/**
	 * i18n messages code
	 */
	// Common
	public static final String ERROR_MSG = "error_msg";

	// Authentication
	public static final String INACTIVE_ACCOUNT = "inactive_account";
	public static final String WRONG_PASS = "wrong_password";
	public static final String WRONG_LOGIN = "wrong_login";

	// Candidate
	public static final String CANDIDATE_NOT_EXIST = "candidate_not_exist";
	public static final String CANDIDATE_FIND_ERROR = "candidate_find_error";
	public static final String QUIZ_NOT_EXIST = "quiz_not_exist";
	public static final String QUIZ_FIND_ERROR = "quiz_find_error";

	// Reclamation
	public static final String RECLAMATION_EXIST = "reclamation_exist";
	public static final String RECLAMATION_CREATED = "reclamation_created";
	public static final String RECLAMATION_NOT_CREATED = "reclamation_not_created";

	// Result
	public static final String RESULT_EXIST = "result_not_exist";
	public static final String RESULT_NOT_EXIST = "result_not_exist";
	public static final String RESULT_FIND_ERROR = "result_find_error";
	public static final String RESULT_CREATED = "result_created";
	public static final String RESULT_NOT_CREATED = "result_not_created";

}
