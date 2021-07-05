package com.express.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.express.model.AnsweredProposition;
import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.OnlineInterview;
import com.express.model.OnlineInterviewQuizResult;
import com.express.model.OnlineInterviewQuizResultId;
import com.express.model.PassedQuestion;
import com.express.model.Quiz;
import com.express.repository.OnlineInterviewQuizResultRepository;
import com.express.service.AnsweredPropositionService;
import com.express.service.AssignedQuizOnlineInterviewService;
import com.express.service.CandidateService;
import com.express.service.OnlineInterviewQuizResultService;
import com.express.service.OnlineInterviewService;
import com.express.service.PassedQuestionService;
import com.express.service.QuizService;

/**
 * Represents serviceImpl of OnlineInterviewQuizResultServiceImpl
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
@Service
public class OnlineInterviewQuizResultServiceImpl implements OnlineInterviewQuizResultService {

	private final OnlineInterviewQuizResultRepository onlineInterviewQuizResultRepository;

	private OnlineInterviewService onlineInterviewService;

	private QuizService quizService;

	private PassedQuestionService passedQuestionService;

	private AnsweredPropositionService answeredPropositionService;

	private AssignedQuizOnlineInterviewService assignedQuizOnlineInterviewService;

	private CandidateService candidateService;

	/**
	 * Constructor of OnlineInterviewQuizResultServiceImpl
	 * 
	 * @param onlineInterviewQuizResultRepository the repository of online interview
	 *                                            quiz result
	 */
	@Autowired
	public OnlineInterviewQuizResultServiceImpl(
			OnlineInterviewQuizResultRepository onlineInterviewQuizResultRepository) {
		this.onlineInterviewQuizResultRepository = onlineInterviewQuizResultRepository;
	}

	/**
	 * Changes OnlineInterview service.
	 * 
	 * @param online interview service.
	 */
	@Autowired
	public void setOnlineInterviewService(OnlineInterviewService onlineInterviewService) {
		this.onlineInterviewService = onlineInterviewService;
	}

	/**
	 * Changes Quiz service.
	 * 
	 * @param quiz service.
	 */
	@Autowired
	public void setQuizService(QuizService quizService) {
		this.quizService = quizService;
	}

	/**
	 * Changes PassedQuestion service.
	 * 
	 * @param Passed question service.
	 */
	@Autowired
	public void setPassedQuestionService(PassedQuestionService passedQuestionService) {
		this.passedQuestionService = passedQuestionService;
	}

	/**
	 * Changes Answered Proposition service.
	 * 
	 * @param Answered Proposition service.
	 */
	@Autowired
	public void setAnsweredPropositionService(AnsweredPropositionService answeredPropositionService) {
		this.answeredPropositionService = answeredPropositionService;
	}

	/**
	 * Changes Assigned Quiz Online Interview Service.
	 * 
	 * @param Assigned Quiz Online Interview Service.
	 */
	@Autowired
	public void setAssignedQuizOnlineInterviewService(
			AssignedQuizOnlineInterviewService assignedQuizOnlineInterviewService) {
		this.assignedQuizOnlineInterviewService = assignedQuizOnlineInterviewService;
	}

	/**
	 * Changes candidate service.
	 * 
	 * @param candidate service.
	 */
	@Autowired
	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	/**
	 * Gets the list of all onlineInterviewQuizResult
	 * 
	 * @return list of all onlineInterviewQuizResult
	 */
	@Override
	public List<OnlineInterviewQuizResult> findAll() {
		return onlineInterviewQuizResultRepository.findAll();
	}

	/**
	 * Gets one onlineInterviewQuizResult by his id
	 * 
	 * @param onlineInterviewQuizResultId id of quiz and id of the online interview
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public OnlineInterviewQuizResult findById(OnlineInterviewQuizResultId onlineInterviewQuizResultId) {
		Optional<OnlineInterviewQuizResult> onlineInterviewQuizResult = onlineInterviewQuizResultRepository
				.findById(onlineInterviewQuizResultId);
		if (onlineInterviewQuizResult.isPresent())
			return onlineInterviewQuizResult.get();
		else {
			throw new NoSuchElementException("\"There is no entity with such ID in the database.\"");
		}

	}

	/**
	 * Creates a new OnlineInterviewQuizResult object
	 * 
	 * @param the OnlineInterviewQuizResult to create
	 * @return the created OnlineInterviewQuizResult
	 * @throws EntityExistsException   if there is already existing entity with such
	 *                                 ID
	 * @throws NoSuchElementException  if no element is present with such ID
	 * @throws EntityNotFoundException if there is no passedQuestion or
	 *                                 answeredProposition with such ID
	 */
	@Override
	public OnlineInterviewQuizResult save(OnlineInterviewQuizResult onlineInterviewQuizResult) {
		if (onlineInterviewQuizResult.getId() != null
				&& onlineInterviewQuizResultRepository.existsById(onlineInterviewQuizResult.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		Set<PassedQuestion> passedQuestions = new HashSet<>();
		Set<PassedQuestion> savedpassedQuestions = new HashSet<>();
		passedQuestions.addAll(onlineInterviewQuizResult.getPassedQuestions());
		onlineInterviewQuizResult.setPassedQuestions(new HashSet<PassedQuestion>());
		OnlineInterview onlineInterview = onlineInterviewService
				.findById(onlineInterviewQuizResult.getId().getOnlineInterviewId());
		Quiz quiz = quizService.findById(onlineInterviewQuizResult.getId().getQuizId());
		onlineInterviewQuizResult.setOnlineInterview(onlineInterview);
		onlineInterviewQuizResult.setQuiz(quiz);
		OnlineInterviewQuizResult createOnlineInterviewQuizResult = onlineInterviewQuizResultRepository
				.save(onlineInterviewQuizResult);

		for (PassedQuestion passedQuestion : passedQuestions) {
			Set<AnsweredProposition> answeredPropositions = new HashSet<>();
			Set<AnsweredProposition> savedAnsweredPropositions = new HashSet<>();
			answeredPropositions.addAll(passedQuestion.getAnsweredProposition());
			passedQuestion.setAnsweredProposition(new HashSet<AnsweredProposition>());
			passedQuestion.setInterviewQuizResult(createOnlineInterviewQuizResult);
			PassedQuestion savedPassedQuestion = passedQuestionService.create(passedQuestion);

			for (AnsweredProposition answeredProposition : answeredPropositions) {
				answeredProposition.setPassedQuestion(savedPassedQuestion);
				AnsweredProposition savedAnsweredProposition = answeredPropositionService.create(answeredProposition);
				savedAnsweredPropositions.add(savedAnsweredProposition);
			}
			savedPassedQuestion.setAnsweredProposition(savedAnsweredPropositions);
			savedpassedQuestions.add(savedPassedQuestion);
			passedQuestionService.update(savedPassedQuestion.getId(), savedPassedQuestion);
		}
		createOnlineInterviewQuizResult.setPassedQuestions(savedpassedQuestions);
		update(createOnlineInterviewQuizResult.getId(), createOnlineInterviewQuizResult);

		Long idCandidate = createOnlineInterviewQuizResult.getOnlineInterview().getApplication().getCandidate().getId();
		List<OnlineInterview> listOnlineInterviews = candidateService.getOnLineInterviews(idCandidate);
		for (OnlineInterview interview : listOnlineInterviews) {
			List<AssignedQuizOnlineInterview> listAssignedQuiz = interview.getAssignedQuizzes();
			for (AssignedQuizOnlineInterview assignedQuiz : listAssignedQuiz) {
				if (assignedQuiz.getId().getQuizId().equals(createOnlineInterviewQuizResult.getId().getQuizId())) {
					assignedQuizOnlineInterviewService.delete(assignedQuiz.getId());
				}
			}
			listAssignedQuiz.removeIf(assignedQuiz -> (assignedQuiz.getId().getQuizId()
					.equals(createOnlineInterviewQuizResult.getId().getQuizId())));
			interview.setAssignedQuizzes(listAssignedQuiz);
			onlineInterviewService.update(interview.getId(), interview);
		}
		return createOnlineInterviewQuizResult;
	}

	/**
	 * Update onlineInterviewQuizResult
	 * 
	 * @param onlineInterviewQuizResultId object
	 * @param id                          id of the OnlineInterviewQuizResultId
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * @throws EntityExistsException   if there is already existing entity with such
	 *                                 ID
	 * @throws NoSuchElementException  if no element is present with such ID
	 */
	@Override
	public OnlineInterviewQuizResult update(OnlineInterviewQuizResultId id,
			OnlineInterviewQuizResult onlineInterviewQuizResult) {
		onlineInterviewQuizResult.setId(id);
		return onlineInterviewQuizResultRepository.save(onlineInterviewQuizResult);
	}

}
