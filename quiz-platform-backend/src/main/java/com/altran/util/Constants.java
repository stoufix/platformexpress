package com.altran.util;

/**
 * Represents constants to use in application
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
public final class Constants {

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
	public static final String PASS_ADMIN = "altran@@2018";

	/**
	 * Error, message and object to return
	 */
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String OBJECT = "object";

	/**
	 * Filters
	 */
	public static final String ACTIVITY_FILTER = "ActivityFilter";
	public static final String TECHNOLOGY_FILTER = "TechnologyFilter";
	public static final String DEGREE_FILTER = "DegreeFilter";
	public static final String ROLE_FILTER = "RoleFilter";
	public static final String USER_FILTER = "UserFilter";
	public static final String QUESTION_FILTER = "QuestionFilter";
	public static final String QUIZ_FILTER = "QuizFilter";
	public static final String CANDIDATE_FILTER = "CandidateFilter";
	public static final String APPLICATION_FILTER = "ApplicationFilter";
	public static final String ONLINEINTERVIEW_FILTER = "OnlineInterviewFilter";
	public static final String ASSIGNEDQUIZONLINEINTERVIEW_FILTER = "AssignedQuizOnlineInterviewFilter";
	public static final String RESULT_FILTER = "ResultFilter";
	public static final String PASSEDQUESTION_FILTER = "PassedQuestionFilter";
	public static final String RECLAMATION_FILTER = "ReclamationFilter";

	/**
	 * Columns for filters
	 */
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String PRIVILEGES = "privileges";
	public static final String FIRSTNAME = "firstName";
	public static final String LASTNAME = "lastName";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	public static final String PHONENUMBER = "phoneNumber";
	public static final String BIRTHDATE = "birthDate";
	public static final String ACTIVITY = "activity";
	public static final String USERNAME = "username";
	public static final String ACTIVATED = "activated";
	public static final String ROLE = "role";
	public static final String CREATEDAT = "createdAt";
	public static final String UPDATEDAT = "updatedAt";
	public static final String SHARED = "shared";
	public static final String CREATEDBY = "createdBy";
	public static final String PROPOSITIONS = "propositions";
	public static final String TECHNOLOGY = "technology";
	public static final String DEGREE = "degree";
	public static final String DURATION = "duration";
	public static final String QUESTIONS = "questions";
	public static final String SUBJECT = "subject";
	public static final String BODY = "body";
	public static final String CONSULTED = "consulted";
	public static final String CANDIDATE = "candidate";
	public static final String ANSWEREDPROPOSITION = "answeredProposition";
	public static final String NOTE = "note";
	public static final String INTERVIEWDATE = "interviewDate";
	public static final String COMMENTS = "comments";
	public static final String QUIZ = "quiz";
	public static final String PASSEDQUESTIONS = "passedQuestions";
	public static final String STATUS = "status";
	public static final String APPLICATION = "application";
	public static final String APPLICATIONS = "applications";
	public static final String GRADUATIONYEAR = "graduationYear";
	public static final String UNIVERSITY = "university";
	public static final String EXPERIENCE = "experience";
	public static final String AVAILABILITYDATE = "availabilityDate";
	public static final String PROFIL = "profil";
	public static final String ASSIGNEDAT = "assignedAt";
	public static final String PASSINGAT = "passingAt";
	public static final String ASSIGNEDQUIZZES = "assignedQuizzes";
	public static final String ASSIGNEDBY = "assignedBy";
	public static final String ONLINEINTERVIEW = "onlineInterview";
	public static final String PASSEDQUIZZES = "passedQuizzes";

	/**
	 * Exceptions messages
	 */
	public static final String NO_ENTITY_DB = "\"There is no entity with such ID in the database.";

	/**
	 * i18n messages code
	 */
	// Common
	public static final String ERROR_MSG = "error_msg";

	// Authentication
	public static final String INACTIVE_ACCOUNT = "inactive_account";
	public static final String WRONG_PASS = "wrong_password";
	public static final String WRONG_LOGIN = "wrong_login";

	// Activity ##
	public static final String ACTIVITY_EXIST = "activity_exist";
	public static final String ACTIVITY_NOT_EXIST = "activity_not_exist";
	public static final String ACTIVITY_FIND_ERROR = "activity_find_error";
	public static final String ACTIVITY_CREATED = "activity_created";
	public static final String ACTIVITY_NOT_CREATED = "activity_not_created";
	public static final String ACTIVITY_UPDATED = "activity_updated";
	public static final String ACTIVITY_NOT_UPDATED = "activity_not_updated";
	public static final String ACTIVITY_DELETED = "activity_deleted";
	public static final String ACTIVITY_NOT_DELETED = "activity_not_deleted";

	// AssignedQuizOnlineInterview
	public static final String ASSIGN_EXIST = "assign_exist";
	public static final String ASSIGN_NOT_EXIST = "assign_not_exist";
	public static final String ASSIGN_SUCCESS = "assign_success";
	public static final String ASSIGN_FAIL = "assign_fail";
	public static final String ASSIGN_UPDATED = "assign_updated";
	public static final String ASSIGN_DELETED = "assign_deleted";
	public static final String ASSIGN_NOT_DELETED = "assign_not_deleted";
	public static final String ALL_ASSIGN_DELETED = "all_assign_deleted";

	// Candidate
	public static final String CANDIDATE_NOT_EXIST = "candidate_not_exist";
	public static final String CANDIDATE_FIND_ERROR = "candidate_find_error";
	public static final String CANDIDATE_ACTIVATE_ACCOUNT = "candidate_activate_account";
	public static final String CANDIDATE_DEACTIVATE_ACCOUNT = "candidate_deactivate_account";

	// Degree
	public static final String DEGREE_EXIST = "degree_exist";
	public static final String DEGREE_NOT_EXIST = "degree_not_exist";
	public static final String DEGREE_FIND_ERROR = "degree_find_error";
	public static final String DEGREE_CREATED = "degree_created";
	public static final String DEGREE_NOT_CREATED = "degree_not_created";
	public static final String DEGREE_UPDATED = "degree_updated";
	public static final String DEGREE_NOT_UPDATED = "degree_not_updated";
	public static final String DEGREE_DELETED = "degree_deleted";
	public static final String DEGREE_NOT_DELETED = "degree_not_deleted";

	// Question
	public static final String QUESTION_EXIST = "question_exist";
	public static final String QUESTION_NOT_EXIST = "question_not_exist";
	public static final String QUESTION_FIND_ERROR = "question_find_error";
	public static final String QUESTION_CREATED = "question_created";
	public static final String QUESTION_NOT_CREATED = "question_not_created";
	public static final String QUESTION_UPDATED = "question_updated";
	public static final String QUESTION_NOT_UPDATED = "question_not_updated";
	public static final String QUESTION_DELETED = "question_deleted";
	public static final String QUESTION_ASSIGNED_DELETE = "question_assigned_delete";
	public static final String QUESTION_NOT_DELETED = "question_not_deleted";
	public static final String QUESTION_VISIBLE = "question_visible";
	public static final String QUESTION_INVISIBLE = "question_invisible";

	// Quiz
	public static final String QUIZ_EXIST = "quiz_exist";
	public static final String QUIZ_NOT_EXIST = "quiz_not_exist";
	public static final String QUIZ_FIND_ERROR = "quiz_find_error";
	public static final String QUIZ_CREATED = "quiz_created";
	public static final String QUIZ_NOT_CREATED = "quiz_not_created";
	public static final String QUIZ_UPDATED = "quiz_updated";
	public static final String QUIZ_NOT_UPDATED = "quiz_not_updated";
	public static final String QUIZ_DELETED = "quiz_deleted";
	public static final String QUIZ_NOT_DELETED = "quiz_not_deleted";
	public static final String QUIZ_VISIBLE = "quiz_visible";
	public static final String QUIZ_INVISIBLE = "quiz_invisible";

	// Reclamation
	public static final String RECLAMATION_EXIST = "reclamation_exist";
	public static final String RECLAMATION_NOT_EXIST = "reclamation_not_exist";
	public static final String RECLAMATION_FIND_ERROR = "reclamation_find_error";
	public static final String RECLAMATION_CREATED = "reclamation_created";
	public static final String RECLAMATION_NOT_CREATED = "reclamation_not_created";
	public static final String RECLAMATION_DELETED = "reclamation_deleted";
	public static final String RECLAMATION_NOT_DELETED = "reclamation_not_deleted";

	// Result
	public static final String RESULT_NOT_EXIST = "result_not_exist";
	public static final String RESULT_FIND_ERROR = "result_find_error";

	// Role
	public static final String ROLE_EXIST = "role_exist";
	public static final String ROLE_NOT_EXIST = "role_not_exist";
	public static final String ROLE_FIND_ERROR = "role_find_error";
	public static final String ROLE_CREATED = "role_created";
	public static final String ROLE_NOT_CREATED = "role_not_created";
	public static final String ROLE_UPDATED = "role_updated";
	public static final String ROLE_NOT_UPDATED = "role_not_updated";
	public static final String ROLE_DELETED = "role_deleted";
	public static final String ROLE_NOT_DELETED = "role_not_deleted";

	// Technology
	public static final String TECHNOLOGY_EXIST = "technology_exist";
	public static final String TECHNOLOGY_NOT_EXIST = "technology_not_exist";
	public static final String TECHNOLOGY_FIND_ERROR = "technology_find_error";
	public static final String TECHNOLOGY_CREATED = "technology_created";
	public static final String TECHNOLOGY_NOT_CREATED = "technology_not_created";
	public static final String TECHNOLOGY_UPDATED = "technology_updated";
	public static final String TECHNOLOGY_NOT_UPDATED = "technology_not_updated";
	public static final String TECHNOLOGY_DELETED = "technology_deleted";
	public static final String TECHNOLOGY_NOT_DELETED = "technology_not_deleted";

	// User
	public static final String USER_EXIST = "user_exist";
	public static final String USER_NOT_EXIST = "user_not_exist";
	public static final String USER_FIND_ERROR = "user_find_error";
	public static final String USER_CREATED = "user_created";
	public static final String USER_NOT_CREATED = "user_not_created";
	public static final String USER_WRONG_MAIL_ADDRESS = "user_wrong_mail_address";
	public static final String USER_MAIL_NOT_SEND = "user_mail_not_send";
	public static final String USER_UPDATED = "user_updated";
	public static final String USER_PASS_UPDATED = "user_pwd_updated";
	public static final String USER_NOT_UPDATED = "user_not_updated";
	public static final String USER_DELETED = "user_deleted";
	public static final String USER_NOT_DELETED = "user_not_deleted";

	// Account
	public static final String ACTIVATE_ACCOUNT = "activate_account";
	public static final String DISABLE_ACCOUNT = "disable_account";

	// OnlineInterview
	public static final String ONLINEINTERVIEW_NOT_EXIST = "onlineInterview_not_exist";
	public static final String ONLINEINTERVIEW_FIND_ERROR = "onlineInterview_find_error";

	// Result
	public static final String RESULT_DELETED = "result_deleted";

}
