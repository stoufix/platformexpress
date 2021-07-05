package com.altran.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altran.model.Activity;
import com.altran.model.Degree;
import com.altran.model.Proposition;
import com.altran.model.Question;
import com.altran.model.Technology;
import com.altran.model.User;
import com.altran.model.filter.QuestionFilter;
import com.altran.repository.QuestionRepository;
import com.altran.service.DegreeService;
import com.altran.service.PropositionService;
import com.altran.service.QuestionService;
import com.altran.service.QuizQuestionService;
import com.altran.service.TechnologyService;
import com.altran.service.UserService;
import static com.altran.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of question service
 * 
 * @author Amal.Smaoui
 * @version 1.0
 */
@Service
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;

	private PropositionService propositionService;

	private UserService userService;

	private TechnologyService technologyService;

	private DegreeService degreeService;
	private QuizQuestionService quizQuestionService;

	/**
	 * Constructor of QuestionServiceImpl
	 * 
	 * @param questionRepository the repository of question
	 */
	@Autowired
	public QuestionServiceImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Autowired
	public void setQuizQuestionService(QuizQuestionService quizQuestionService) {
		this.quizQuestionService = quizQuestionService;
	}

	/**
	 * Changes proposition service.
	 * 
	 * @param propositionService proposition service.
	 */
	@Autowired
	public void setPropositionService(PropositionService propositionService) {
		this.propositionService = propositionService;
	}

	/**
	 * Changes technology service.
	 * 
	 * @param technologyService technology service.
	 */
	@Autowired
	public void setTechnologyService(TechnologyService technologyService) {
		this.technologyService = technologyService;
	}

	/**
	 * Changes degree service.
	 * 
	 * @param degreeService degree service.
	 */
	@Autowired
	public void setDegreeService(DegreeService degreeService) {
		this.degreeService = degreeService;
	}

	/**
	 * Changes user service.
	 * 
	 * @param userService user service.
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Gets the list of all questions
	 * 
	 * @return list of all questions
	 */
	@Override
	public List<Question> findAll() {
		return questionRepository.findAllByOrderByCreatedAtDesc();
	}

	/**
	 * Gets the list of all questions by page
	 * 
	 * @param pageable pagination information
	 * @return list of all questions by page
	 */
	public Page<Question> findAllByPage(Pageable pageable) {
		return questionRepository.findAllByOrderByCreatedAtDesc(pageable);
	}

	/**
	 * Gets the list of visible questions and questions created by authenticated
	 * user by page
	 * 
	 * @param username the user name of connected user
	 * @param pageable pagination information
	 * @return list of visible questions by page
	 */
	@Override
	public Page<Question> findAllVisibleQuestions(String username, Pageable pageable) {
		// Get user by userName
		User user = userService.findByUsername(username);
		// Get all questions
		List<Question> questions = findAll();
		List<Question> result;
		// Lambda expression to filter visible questions
		result = questions.stream().filter(
				question -> (question.getShared()) || ((!(question.getShared())) && (question.getCreatedBy() == user)))
				.collect(Collectors.toCollection(ArrayList::new));
		// convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Question> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());
	}

	/**
	 * Gets the list of visible questions, questions created by authenticated user
	 * and by quiz technology and degree
	 * 
	 * @param username     the user name of connected user
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @param pageable     pagination information
	 * @return list of visible questions by page
	 */
	@Override
	public Page<Question> findAllVisibleQuestionsByTechnologyAndDegree(Long technologyId, Long degreeId,
			String username, Pageable pageable) {
		// Get user by userName
		User user = userService.findByUsername(username);
		// Get all questions
		List<Question> questions = findAll();
		List<Question> result;
		// Lambda expression to filter visible questions
		result = questions.stream().filter(question -> (

		question.getTechnology().getId().equals(technologyId) && question.getDegree().getId().equals(degreeId) &&

				(question.getShared() || ((!(question.getShared())) && (question.getCreatedBy() == user))))

		).collect(Collectors.toCollection(ArrayList::new));

		// convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Question> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());
	}

	/**
	 * Gets the list of all visible questions and by activity of authenticated user
	 * 
	 * @param username the user name of connected user
	 * @param pageable pagination information
	 * @return list of visible questions by page
	 */
	@Override
	public Page<Question> findAllVisibleQuestionsByAuthActivity(String username, Pageable pageable) {
		// Get user by userName
		User user = userService.findByUsername(username);
		// Get all questions
		List<Question> questions = findAll();
		List<Question> result;
		// Lambda expression to filter visible questions and by activity question
		result = questions.stream()
				.filter(question -> (question.getShared()
						&& (question.getActivity().getTitle() == user.getActivity().getTitle())
						|| ((!(question.getShared())) && (question.getCreatedBy() == user))
						|| ((question.getShared()) && (question.getCreatedBy() == user))))
				.collect(Collectors.toCollection(ArrayList::new));
		// convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Question> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());
	}

	/**
	 * Gets the list of all visible questions by activity of authenticated user and
	 * by quiz technology and degree
	 * 
	 * @param username     the user name of connected user
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @param pageable     pagination information
	 * 
	 * @return list of visible questions by page
	 */
	@Override
	public Page<Question> findAllVisibleQuestionsByAuthActivityTechnologyAndDegree(Long technologyId, Long degreeId,
			String username, Pageable pageable) {
		// Get user by userName
		User user = userService.findByUsername(username);
		// Get all questions
		List<Question> questions = findAll();
		List<Question> result;
		// Lambda expression to filter visible questions by activity, quiz technology
		// and degree
		result = questions.stream().filter(question -> (

		question.getTechnology().getId().equals(technologyId) && question.getDegree().getId().equals(degreeId) &&

				(question.getShared() && (question.getActivity().getTitle() == user.getActivity().getTitle())
						|| ((!(question.getShared())) && (question.getCreatedBy() == user))))

		).collect(Collectors.toCollection(ArrayList::new));
		// convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Question> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());
	}

	/**
	 * Gets one question by his id
	 * 
	 * @param id the id of the question
	 * @return question object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Question findById(Long id) {
		Optional<Question> question = questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Gets the list of questions by there Technologies and degrees
	 * 
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @return list of questions object with the same technology and degree
	 */
	@Override
	public List<Question> findByTechnologyAndDegree(Long technologyId, Long degreeId) {
		List<Question> questionsResult = new ArrayList<>();
		List<Question> questions = questionRepository.findAll();
		for (Question question : questions) {
			if (question.getTechnology().getId().equals(technologyId)
					&& question.getDegree().getId().equals(degreeId)) {
				questionsResult.add(question);
			}
		}
		return questionsResult;
	}

	/**
	 * Gets the list of questions by there technologies and degrees by page
	 * 
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @param pageable     pagination information
	 * @return list of questions object with the same technology and degree
	 */
	@Override
	public Page<Question> findByPageByTechnologyAndDegree(Long technologyId, Long degreeId, Pageable pageable) {

		List<Question> questionsResult = new ArrayList<>();
		List<Question> questions = questionRepository.findAll();
		for (Question question : questions) {
			if (question.getTechnology().getId().equals(technologyId)
					&& question.getDegree().getId().equals(degreeId)) {
				questionsResult.add(question);
			}
		}
		// convert list to page
		if (pageable.getOffset() >= questionsResult.size()) {
			return Page.empty();
		}

		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > questionsResult.size()
				? questionsResult.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Question> subList = questionsResult.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, questionsResult.size());
	}

	/**
	 * Creates a new question and all of his propositions
	 * 
	 * @param question the question to create
	 * @return the created question
	 * @throws EntityExistsException   if there is already existing question or
	 *                                 proposition with such ID
	 * @throws EntityNotFoundException if there is no technology or user with such
	 *                                 ID
	 * @throws NoSuchElementException  if no element is present with such ID
	 * 
	 */
	@Transactional
	@Override
	public Question create(Question question) {
		if (question.getId() != null && questionRepository.existsById(question.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		Set<Proposition> propositions = question.getPropositions();
		// set question technology to display technology without refresh
		Technology questionTechnology = technologyService.findById(question.getTechnology().getId());
		question.setTechnology(questionTechnology);
		// set question degree to display degree without refresh
		Degree questionDegree = degreeService.findById(question.getDegree().getId());
		question.setDegree(questionDegree);
		// save question
		Question createdQuestion = questionRepository.save(question);
		// create proposition from the question in the database
		if (createdQuestion != null) {
			for (Proposition proposition : propositions) {
				proposition.setQuestion(createdQuestion);
				this.propositionService.create(proposition);
			}
			// Add question to user createdQuestion list
			User createdBy = userService.findById(createdQuestion.getCreatedBy().getId());
			createdBy.getCreatedQuestions().add(createdQuestion);
			userService.update(createdBy, createdBy.getId());
		}
		return createdQuestion;
	}

	/**
	 * Updates one question and deletes all of the old question's proposition and
	 * creates all the new propositions to insure the update of the question and his
	 * propositions
	 * 
	 * @param id       the id of the updated question
	 * @param question the new question object with the new values
	 * @return the updated question
	 * @throws EntityNotFoundException if there is no question or proposition with
	 *                                 such ID
	 * @throws EntityExistsException   if there is already existing proposition with
	 *                                 such ID
	 */
	@Transactional
	@Override
	public Question update(Long id, Question question) {
		if (id != null && !questionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		Question oldQuestion = findById(id);
		Set<Proposition> oldPropositions = oldQuestion.getPropositions();
		// delete old proposition from database
		for (Proposition proposition : oldPropositions) {
			propositionService.delete(proposition.getId());
		}
		// update the question
		question.setId(id);
		// save the new propositions in database
		Set<Proposition> propositions = question.getPropositions();

		for (Proposition proposition : propositions) {
			proposition.setQuestion(question);
			this.propositionService.create(proposition);
		}

		// get the same create date as the old question(Fix null problem)
		Date createdDate = oldQuestion.getCreatedAt();
		question.setCreatedAt(createdDate);
		return questionRepository.save(question);
	}

	/**
	 * Updates question without updating proposition and used for quiz updating and
	 * adding questions without propositions management
	 * 
	 * @param id: the id of the updated question
	 * @throws EntityNotFoundException if there is no entity with such IDs
	 */
	@Override
	public Question simpleUpdate(Long id, Question question) {
		if (id != null && !questionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		question.setId(id);
		return questionRepository.save(question);
	}

	/**
	 * Deletes one question
	 * 
	 * @param id the id of the deleted question
	 * @throws EntityNotFoundException if there is no question or proposition with
	 *                                 such ID
	 */
	@Override
	public Boolean delete(Long id) {
		if (id != null && !questionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		if (quizQuestionService.existQuestionInQuizzes(this.findById(id))) {
			Question question = findById(id);
			// delete saved propositions
			Set<Proposition> propositions = question.getPropositions();
			// delete question from user createdQuestion list
			for (Proposition proposition : propositions) {
				propositionService.delete(proposition.getId());
			}
			User user = question.getCreatedBy();
			List<Question> createdQuestion = user.getCreatedQuestions();
			createdQuestion.remove(question);
			userService.update(user, user.getId());
			// delete question
			questionRepository.deleteById(id);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Searches for questions by one term without activity
	 * 
	 * @param term     the term to base search on it
	 * @param username the user name of connected user
	 * @param username the user name of connected user
	 * @return list of questions contains the input term by page
	 */

	@Override
	public Page<Question> simpleSearchWithoutActivity(String username, String term, Pageable pageable) {
		return questionRepository.simpleSearchWithoutActivity(username, term, pageable);
	}

	/**
	 * Searches for questions by one term with activity
	 * 
	 * @param term     the term to base search on it
	 * @param username the user name of connected user
	 * @return list of questions contains the input term by page
	 * @throws UsernameNotFoundException if there is no user with such UserName
	 */
	@Override
	public Page<Question> simpleSearchWithActivity(String username, String term, Pageable pageable) {
		User user = userService.findByUsername(username);
		String activity = user.getActivity().getTitle();
		return questionRepository.simpleSearchWithActivity(username, activity, term, pageable);
	}

	/**
	 * Searches for questions by one term without activity, with technology and
	 * degree
	 * 
	 * @param term            the term to base search on it
	 * @param username        the user name of connected user
	 * @param technologyTitle technology's title
	 * @param degreeTitle     degree's title
	 * @return list of questions contains the input term by page
	 */

	@Override
	public Page<Question> simpleSearchWithoutActivityWithTechnologyAndDegree(String username, String term,
			Long technology, Long degree, Pageable pageable) {
		return questionRepository.simpleSearchWithoutActivityWithTechnologyAndDegree(username, term, technology, degree,
				pageable);
	}

	/**
	 * Searches for questions by one term with activity, with technology and degree
	 * 
	 * @param term            the term to base search on it
	 * @param username        the user name of connected user
	 * @param technologyTitle technology's title
	 * @param degreeTitle     degree's title
	 * @return list of questions contains the input term by page
	 * @throws UsernameNotFoundException if there is no user with such UserName
	 */
	@Override
	public Page<Question> simpleSearchWithActivityTechnologyAndDegree(String username, String term, Long technology,
			Long degree, Pageable pageable) {
		User user = userService.findByUsername(username);
		String activity = user.getActivity().getTitle();
		return questionRepository.simpleSearchWithActivityTechnologyAndDegree(username, activity, term, technology,
				degree, pageable);
	}

	/**
	 * Searches for questions by one term by user
	 * 
	 * @param term the term to base search on it
	 * @return list of questions contains the input term by page
	 */
	@Override
	public Page<Question> simpleSearchByUser(String username, String term, Pageable pageable) {
		return questionRepository.simpleSearchByUser(username, term, pageable);
	}

	/**
	 * Changes question visibility for other users
	 * 
	 * @param id the of the updated question
	 * @throws NoSuchElementException if no element is question with such ID
	 */
	@Override
	public Question changeShared(Long id) {
		Question question = findById(id);
		if (question.getShared())
			question.setShared(false);
		else
			question.setShared(true);
		return questionRepository.save(question);
	}

	/**
	 * Searches for questions by multiple terms
	 * 
	 * @param questionFilter question filter object with list of advanced search
	 *                       criteria
	 * @param pagination     information
	 */
	@Override
	public Page<Question> advancedSearch(QuestionFilter questionFilter, Pageable pageable) {
		List<Technology> technologies = questionFilter.getTechnologies();
		List<Long> technologiesId;
		List<Long> degreesId;
		List<Long> activitiesId;
		List<Long> usersId;

		// Gets all technologies id
		if (technologies.isEmpty()) {
			technologiesId = null;
		} else {
			technologiesId = technologies.stream().map(Technology::getId)
					.collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all degrees id
		List<Degree> degrees = questionFilter.getDegrees();
		if (degrees.isEmpty()) {
			degreesId = null;
		} else {
			degreesId = degrees.stream().map(Degree::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all activities id
		List<Activity> activities = questionFilter.getActivities();
		if (activities.isEmpty()) {
			activitiesId = null;
		} else {
			activitiesId = activities.stream().map(Activity::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all users id
		List<User> users = questionFilter.getNames();
		if (users.isEmpty()) {
			usersId = null;
		} else {
			usersId = users.stream().map(User::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		return questionRepository.advancedSearch(technologiesId, degreesId, activitiesId, usersId, pageable);
	}

	/**
	 * Searches for questions by multiple terms
	 * 
	 * @param questionFilter question filter object with list of advanced search
	 *                       criteria
	 * @param pagination     information
	 */
	@Override
	public Page<Question> advancedSearchWithActivity(QuestionFilter questionFilter, String username,
			Pageable pageable) {
		User user = userService.findByUsername(username);
		String activity = user.getActivity().getTitle();

		List<Technology> technologies = questionFilter.getTechnologies();
		List<Long> technologiesId;
		List<Long> degreesId;
		List<Long> activitiesId;
		List<Long> usersId;

		// Gets all technologies id
		if (technologies.isEmpty()) {
			technologiesId = null;
		} else {
			technologiesId = technologies.stream().map(Technology::getId)
					.collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all degrees id
		List<Degree> degrees = questionFilter.getDegrees();
		if (degrees.isEmpty()) {
			degreesId = null;
		} else {
			degreesId = degrees.stream().map(Degree::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all activities id
		List<Activity> activities = questionFilter.getActivities();
		if (activities.isEmpty()) {
			activitiesId = null;
		} else {
			activitiesId = activities.stream().map(Activity::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all users id
		List<User> users = questionFilter.getNames();
		if (users.isEmpty()) {
			usersId = null;
		} else {
			usersId = users.stream().map(User::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		return questionRepository.advancedSearchWithActivity(technologiesId, degreesId, activitiesId, usersId, username,
				activity, pageable);
	}

	/**
	 * Searches for questions created by connected user by multiple terms
	 * 
	 * @param questionFilter question filter object with list of advanced search
	 *                       criteria
	 * @param userName       userName of the connected user
	 * @param pagination     informationD
	 */
	@Override
	public Page<Question> advancedSearchByUser(QuestionFilter questionFilter, String username, Pageable pageable) {
		List<Technology> technologies = questionFilter.getTechnologies();
		List<Long> technologiesId = new ArrayList<>();
		List<Degree> degrees = questionFilter.getDegrees();
		List<Long> degreesId = new ArrayList<>();
		// Gets all technologies id
		if (technologies == null || technologies.isEmpty()) {
			technologiesId = null;
		} else {
			for (Technology technology : technologies) {
				technologiesId.add(technology.getId());
			}
		}
		// Gets all degrees id
		if (degrees == null || degrees.isEmpty()) {
			degreesId = null;
		} else {
			for (Degree degree : degrees) {
				degreesId.add(degree.getId());
			}
		}
		return questionRepository.advancedSearchByUser(technologiesId, degreesId, username, pageable);
	}

}
