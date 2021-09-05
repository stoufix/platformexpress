package com.express.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.express.model.Activity;
import com.express.model.Degree;
import com.express.model.Question;
import com.express.model.Quiz;
import com.express.model.QuizQuestion;
import com.express.model.QuizQuestionId;
import com.express.model.Technology;
import com.express.model.User;
import com.express.model.filter.QuizFilter;
import com.express.repository.QuizRepository;
import com.express.service.QuestionService;
import com.express.service.QuizQuestionService;
import com.express.service.QuizService;
import com.express.service.UserService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of quiz service
 * 

 * @version 1.0
 */
@Service
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;

	private QuestionService questionService;

	private UserService userService;

	private QuizQuestionService quizQuestionService;

	/**
	 * Constructor of QuizServiceImpl
	 * 
	 * @param quizRepository the repository of quiz
	 */
	@Autowired
	public QuizServiceImpl(QuizRepository quizRepository) {
		this.quizRepository = quizRepository;
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
	 * Changes user service.
	 * 
	 * @param userService user service.
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Changes quiz question service.
	 * 
	 * @param quizQuestionService quiz question service.
	 */
	@Autowired
	public void setQuizQuestionService(QuizQuestionService quizQuestionService) {
		this.quizQuestionService = quizQuestionService;
	}

	/**
	 * Gets the list of all quizzes
	 * 
	 * @return list of all quizzes
	 */
	@Override
	public List<Quiz> findAll() {
		return quizRepository.findAllByOrderByCreatedAtDesc();
	}

	/**
	 * Gets the list of all quizzes by page and sorted by created date
	 * 
	 * @param pageable pagination information
	 * @return list of all quizzes by page and sorted by created date
	 */
	public Page<Quiz> findAllByPage(Pageable pageable) {
		return quizRepository.findAllByOrderByCreatedAtDesc(pageable);
	}

	/**
	 * Gets the list of visible quizzes by page
	 * 
	 * @param pageable pagination information
	 * @return list of visible quizzes by page
	 */
	public Page<Quiz> findVisibleByPage(String username, Pageable pageable) {
		// Gets the user by userName
		User user = userService.findByUsername(username);
		List<Quiz> quizzes = findAll();
		List<Quiz> result;
		result = quizzes.stream()
				.filter(quiz -> (quiz.getShared()) || ((!(quiz.getShared())) && (quiz.getCreatedBy() == user)))
				.collect(Collectors.toCollection(ArrayList::new));
		// Convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Quiz> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());

	}

	/**
	 * Gets the list of all visible quizzes and by activity of authenticated user
	 * 
	 * @param pageable pagination information
	 * @return list of visible quizzes by page
	 * @throws UsernameNotFoundException if there is no user with such userName
	 */

	@Override
	public Page<Quiz> findVisibleByAuthActivity(String username, Pageable pageable) {
		// Gets the user by userName
		User user = userService.findByUsername(username);
		List<Quiz> quizzes = findAll();
		List<Quiz> result;
		// Lambda expression to filter visible questions and by activity quiz
		result = quizzes.stream()
				.filter(quiz -> (quiz.getShared() && (quiz.getActivity().getTitle() == user.getActivity().getTitle()))
						|| ((!(quiz.getShared())) && (quiz.getCreatedBy() == user)))
				.collect(Collectors.toCollection(ArrayList::new));
		// Convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<Quiz> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());

	}

	/**
	 * Gets one quiz by his id
	 * 
	 * @param id the id of the quiz
	 * @return question object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Quiz findById(Long id) {
		Optional<Quiz> quiz = quizRepository.findById(id);
		if (quiz.isPresent())
			return quiz.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new quiz without questions
	 * 
	 * @param quiz the quiz to create
	 * @return the created quiz
	 * @throws EntityExistsException   if there is already existing entity with such
	 *                                 ID
	 * @throws NoSuchElementException  if no user is present with such ID
	 * @throws EntityNotFoundException if there is no user with such ID
	 * 
	 */
	@Override
	public Quiz create(Quiz quiz) {
		if (quiz.getId() != null && quizRepository.existsById(quiz.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		Quiz createdQuiz = quizRepository.save(quiz);
		// Add quiz to user createdQuizzes list
		User createdBy = userService.findById(createdQuiz.getCreatedBy().getId());
		createdBy.getCreatedQuizzes().add(createdQuiz);
		userService.update(createdBy, createdBy.getId());
		return createdQuiz;
	}

	/**
	 * Update quiz and Adds questions(QuizQuestion model) to the quiz
	 * 
	 * @param id       the id of the updated quiz
	 * @param question the new quiz object with the new values
	 * @return the updated quiz
	 * @throws EntityNotFoundException if there is no entity with such ID in the
	 *                                 database
	 * @throws NoSuchElementException  if no quiz of question is present with such
	 *                                 ID
	 */
	@Override
	public Quiz update(Long id, Quiz quiz) {
		if (id != null && !quizRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		Question question;
		QuizQuestionId quizQuestionId = new QuizQuestionId();
		Long questionId;
		quiz.setId(id);
		// Get List of quizQuestion from quiz
		List<QuizQuestion> quizQuestions = quiz.getQuestions();

		// Delete all questions assigned to this quiz
		Quiz savedQuiz = findById(id);
		for (QuizQuestion quizQuestion : savedQuiz.getQuestions()) {
			quizQuestionService.delete(quizQuestion.getId());
		}
		if (quizQuestions != null && !quizQuestions.isEmpty()) {
			for (QuizQuestion quizQuestion : quizQuestions) {

				// get the id of the question
				questionId = quizQuestion.getQuestion().getId();
				// find By Id the question from quizQuestion
				question = questionService.findById(questionId);

				quizQuestionId.setQuestionId(questionId);
				quizQuestionId.setQuizId(id);
				quizQuestion.setId(quizQuestionId);

				// set quiz and question of quizQuestion with the updated quiz and the question
				// then create this quizQuestion
				quizQuestion.setquiz(quiz);
				quizQuestion.setQuestion(question);
				quizQuestionService.create(quizQuestion);
			}
		}
		// get the same create date as the old quiz(Fix null problem)
		Quiz oldQuiz = findById(id);
		Date createdDate = oldQuiz.getCreatedAt();
		quiz.setCreatedAt(createdDate);
		// get the same createdBy as the old quiz(Fix null problem)
		User createdBy = oldQuiz.getCreatedBy();
		quiz.setCreatedBy(createdBy);

		return quizRepository.save(quiz);

	}

	/**
	 * Deletes one quiz
	 * 
	 * @param id the id of the deleted quiz
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long quizId) {
		if (quizId != null && !quizRepository.existsById(quizId)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		Quiz quiz = findById(quizId);
		// delete all quizQuestion of this quiz
		List<QuizQuestion> quizQuestions = quiz.getQuestions();
		for (QuizQuestion quizQuestion : quizQuestions) {
			quizQuestionService.delete(quizQuestion.getId());
		}
		// delete quiz from user createdQuiz list
		User user = quiz.getCreatedBy();
		List<Quiz> createdQuizzes = user.getCreatedQuizzes();
		createdQuizzes.remove(quiz);
		userService.update(user, user.getId());
		// delete quiz by id
		quizRepository.deleteById(quizId);

	}

	/**
	 * Changes quiz visibility for other users
	 * 
	 * @param id the of the updated quiz
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Quiz changeShared(Long id) {
		Quiz quiz = findById(id);
		if (quiz.getShared())
			quiz.setShared(false);
		else
			quiz.setShared(true);
		return quizRepository.save(quiz);
	}

	/**
	 * Searches for quizzes by one term without activity
	 * 
	 * @param term the term to base search on it
	 * @return list of questions contains the input term by page
	 */

	@Override
	public Page<Quiz> simpleSearchWithoutActivity(String username, String term, Pageable pageable) {
		return quizRepository.simpleSearchWithoutActivity(username, term, pageable);
	}

	/**
	 * Searches for quizzes by one term with activity
	 * 
	 * @param term the term to base search on it
	 * @return list of questions contains the input term by page
	 * @throws UsernameNotFoundException if there is no user with such UserName
	 */

	@Override
	public Page<Quiz> simpleSearchWithActivity(String username, String term, Pageable pageable) {
		User user = userService.findByUsername(username);
		String activity = user.getActivity().getTitle();
		return quizRepository.simpleSearchWithActivity(username, activity, term, pageable);
	}

	/**
	 * Searches for quizzes by one term by user
	 * 
	 * @param term the term to base search on it
	 * @return list of questions contains the input term by page
	 */

	@Override
	public Page<Quiz> simpleSearchByUser(String username, String term, Pageable pageable) {
		return quizRepository.simpleSearchByUser(username, term, pageable);
	}

	/**
	 * Searches for quizzes by multiple terms
	 * 
	 * @param quizFilter quiz filter object with list of advanced search criteria
	 * @param pagination information
	 */
	@Override
	public Page<Quiz> advancedSearch(QuizFilter quizFilter, Pageable pageable) {
		List<Technology> technologies = quizFilter.getTechnologies();
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
		List<Degree> degrees = quizFilter.getDegrees();
		if (degrees.isEmpty()) {
			degreesId = null;
		} else {
			degreesId = degrees.stream().map(Degree::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all activities id
		List<Activity> activities = quizFilter.getActivities();
		if (activities.isEmpty()) {

			activitiesId = null;
		} else {
			activitiesId = activities.stream().map(Activity::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all users id
		List<User> users = quizFilter.getNames();
		if (users.isEmpty()) {
			usersId = null;
		} else {
			usersId = users.stream().map(User::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		return quizRepository.advancedSearch(technologiesId, degreesId, activitiesId, usersId, pageable);
	}

	/**
	 * Searches for questions by multiple terms
	 * 
	 * @param questionFilter question filter object with list of advanced search
	 *                       criteria
	 * @param pagination     information
	 */
	@Override
	public Page<Quiz> advancedSearchWithActivity(QuizFilter quizFilter, String username, Pageable pageable) {
		User user = userService.findByUsername(username);
		String activity = user.getActivity().getTitle();

		List<Technology> technologies = quizFilter.getTechnologies();
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
		List<Degree> degrees = quizFilter.getDegrees();
		if (degrees.isEmpty()) {
			degreesId = null;
		} else {
			degreesId = degrees.stream().map(Degree::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all activities id
		List<Activity> activities = quizFilter.getActivities();
		if (activities.isEmpty()) {
			activitiesId = null;
		} else {
			activitiesId = activities.stream().map(Activity::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		// Gets all users id
		List<User> users = quizFilter.getNames();
		if (users.isEmpty()) {
			usersId = null;
		} else {
			usersId = users.stream().map(User::getId).collect(Collectors.toCollection(ArrayList::new));
		}
		return quizRepository.advancedSearchWithActivity(technologiesId, degreesId, activitiesId, usersId, username,
				activity, pageable);
	}

	/**
	 * Searches for quizzes created by connected user by multiple terms
	 * 
	 * @param quizFilter quiz filter object with list of advanced search criteria
	 * @param userName   userName of the connected user
	 * @param pagination informationD
	 */
	@Override
	public Page<Quiz> advancedSearchByUser(QuizFilter quizFilter, String username, Pageable pageable) {
		List<Technology> technologies = quizFilter.getTechnologies();
		List<Long> technologiesId = new ArrayList<>();
		List<Degree> degrees = quizFilter.getDegrees();
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
		return quizRepository.advancedSearchByUser(technologiesId, degreesId, username, pageable);
	}

}
