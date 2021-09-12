package com.express.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.AssignedQuizOnlineInterviewId;
import com.express.model.Candidate;
import com.express.model.OnlineInterview;
import com.express.model.Quiz;
import com.express.repository.AssignedQuizOnlineInterviewRepository;
import com.express.service.AssignedQuizOnlineInterviewService;
import com.express.service.CandidateService;
import com.express.service.OnlineInterviewService;
import com.express.service.QuizService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of AssignedQuizOnlineInterview service
 * 
 Maha.BSaid
 * @version 1.0
 */
@Service
public class AssignedQuizOnlineInterviewServiceImpl implements AssignedQuizOnlineInterviewService {

	private final AssignedQuizOnlineInterviewRepository assignedQuizOnlineInterviewRepository;

	private OnlineInterviewService onlineInterviewService;

	private QuizService quizService;

	private CandidateService candidateService;

	/**
	 * Constructor of AssignedQuizOnlineInterviewServiceImpl
	 * 
	 * @param assignedQuizOnlineInterviewRepository the repository of
	 *                                              AssignQuizOnlineInterview
	 */
	@Autowired
	public AssignedQuizOnlineInterviewServiceImpl(
			AssignedQuizOnlineInterviewRepository assignedQuizOnlineInterviewRepository) {
		this.assignedQuizOnlineInterviewRepository = assignedQuizOnlineInterviewRepository;
	}

	/**
	 * Changes online interview service.
	 * 
	 * @param onlineInterviewService online interview service.
	 */
	@Autowired
	public void setOnlineInterviewService(OnlineInterviewService onlineInterviewService) {
		this.onlineInterviewService = onlineInterviewService;
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
	 * Changes candidate service.
	 * 
	 * @param candidateService candidate service.
	 */
	@Autowired
	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	/**
	 * Gets the list of onlineInterviews with assigned quiz
	 * 
	 * @param pageable pagination information
	 * @return list of onlineInterview with list of assigned quizzes by page
	 */
	@Override
	public Page<OnlineInterview> findOnLineInterviewsWithAssignedQuizzes(Pageable pageable) {
		List<OnlineInterview> onLineInterviews = onlineInterviewService.findAll();
		List<OnlineInterview> result;
		// Lambda expression to filter onlineInterviews with assigned quizzes
		result = onLineInterviews.stream()
				.filter(onLineInterview -> (onLineInterview.getAssignedQuizzes() != null)
						&& (!onLineInterview.getAssignedQuizzes().isEmpty()))
				.collect(Collectors.toCollection(ArrayList::new));
		// convert list to page
		if (pageable.getOffset() >= result.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > result.size() ? result.size()
				: pageable.getOffset() + pageable.getPageSize());
		List<OnlineInterview> subList = result.subList(startIndex, endIndex);
		return new PageImpl<>(subList, pageable, result.size());
	}

	/**
	 * Gets one assignedQuizOnlineInterview by his id
	 * 
	 * @param quizOnlineInterviewId id the id of the assignedQuizOnlineInterview
	 *                              composed by id of assignedQuiz and id of
	 *                              onlineInterview
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public AssignedQuizOnlineInterview findById(AssignedQuizOnlineInterviewId quizOnlineInterviewId) {
		Optional<AssignedQuizOnlineInterview> assignedQuizOnlineInterview = assignedQuizOnlineInterviewRepository
				.findById(quizOnlineInterviewId);
		if (assignedQuizOnlineInterview.isPresent())
			return assignedQuizOnlineInterview.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new AssignedQuizOnlineInterview object
	 * 
	 * @param activity the AssignedQuizOnlineInterview to create
	 * @return the created AssignedQuizOnlineInterview
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public AssignedQuizOnlineInterview create(AssignedQuizOnlineInterview assignedQuizOnlineInterview) {
		if (assignedQuizOnlineInterview.getId() != null
				&& assignedQuizOnlineInterviewRepository.existsById(assignedQuizOnlineInterview.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		return assignedQuizOnlineInterviewRepository.save(assignedQuizOnlineInterview);
	}

	/**
	 * Updates one AssignedQuizOnlineInterview object
	 * 
	 * @param quizId                      the id of the quiz to assign
	 * @param onlineInterviewId           the id of the interview to assign to
	 * @param assignedQuizOnlineInterview the new assignedQuizOnlineInterview object
	 *                                    with the new values
	 * @return the updated assignedQuizOnlineInterview
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public AssignedQuizOnlineInterview update(Long quizId, Long onlineInterviewId,
			AssignedQuizOnlineInterview assignedQuizOnlineInterview) {
		AssignedQuizOnlineInterviewId id = new AssignedQuizOnlineInterviewId();
		id.setQuizId(quizId);
		id.setOnlineInterviewId(onlineInterviewId);
		assignedQuizOnlineInterview.setId(id);
		return assignedQuizOnlineInterviewRepository.save(assignedQuizOnlineInterview);
	}

	/**
	 * Assign list of quizzes to an online interview
	 * 
	 * @param onlineInterviews Online interview object with the assigned quizzes
	 *                         list
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * @throws EntityExistsException   if there is already existing entity with such
	 *                                 ID
	 */
	@Override
	public void assignQuizzesToOneOnlineInterview(OnlineInterview onlineInterview) {
		List<AssignedQuizOnlineInterview> assignedQuizzes = onlineInterview.getAssignedQuizzes();
		if (assignedQuizzes != null && !assignedQuizzes.isEmpty()) {
			// get candidate to assign quizzes
			Candidate candidate = onlineInterview.getApplication().getCandidate();
			for (AssignedQuizOnlineInterview assignedQuiz : assignedQuizzes) {
				// Get quiz by Id
				Long quizId = assignedQuiz.getId().getQuizId();
				Quiz savedQuiz = quizService.findById(quizId);
				if (!assignedQuizOnlineInterviewRepository.existsById(assignedQuiz.getId())) {
					// Set quiz and user then save assignedQuiz
					assignedQuiz.setQuiz(savedQuiz);
					assignedQuiz.setOnlineInterview(onlineInterview);
					create(assignedQuiz);
				}
			}
			if (onlineInterview.getStatus().equals("initialized")) {
				candidateService.createAccountCandidate(candidate);
			} else if (!candidate.isActivated()) {
				candidate.setActivated(true);
				candidateService.update(candidate, candidate.getId());
			}
			// set status of onlineInterview
			onlineInterview.setStatus("inProgress");
			onlineInterviewService.update(onlineInterview.getId(), onlineInterview);
		}
	}

	/**
	 * Assign list of quizzes to online interviews
	 * 
	 * @param onlineInterviews Online interviews objects with the assigned quizzes
	 *                         list
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * @throws EntityExistsException   if there is already existing entity with such
	 *                                 ID
	 */
	@Override
	public void assignQuizzes(List<OnlineInterview> onlineInterviews) {
		for (OnlineInterview onlineInterview : onlineInterviews) {
			assignQuizzesToOneOnlineInterview(onlineInterview);
		}
	}

	/**
	 * Update list of assigned quizzes of an online interview
	 * 
	 * @param onlineInterview Online interview object with the assigned quizzes list
	 * @param id              id of the on line interview
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * @throws EntityExistsException   if there is already existing entity with such
	 *                                 ID
	 * @throws NoSuchElementException  if no element is present with such ID
	 */
	@Override
	@Transactional
	public void updateAssignedQuizzes(Long id, OnlineInterview onlineInterview) {
		deleteAssignedQuizzes(id);
		assignQuizzesToOneOnlineInterview(onlineInterview);
	}

	/**
	 * Delete one assigned quiz from an online interview
	 * 
	 * @param assignQuizOnlineInterviewId id of the assigned quiz and id of the
	 *                                    online interview
	 */
	@Override
	@Transactional
	public void deleteAssignedQuiz(AssignedQuizOnlineInterviewId assignQuizOnlineInterviewId) {
		if (assignQuizOnlineInterviewId != null
				&& !assignedQuizOnlineInterviewRepository.existsById(assignQuizOnlineInterviewId)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		assignedQuizOnlineInterviewRepository.deleteById(assignQuizOnlineInterviewId);
	}

	/**
	 * Delete all assigned quizzes form an online interview
	 * 
	 * @param onlineInterviewId id of the interview
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	@Transactional
	public void deleteAssignedQuizzes(Long onlineInterviewId) {
		if (onlineInterviewId != null && !onlineInterviewService.existsById(onlineInterviewId)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		List<AssignedQuizOnlineInterview> assignedQuizzes = assignedQuizOnlineInterviewRepository.findAll();
		Iterator<AssignedQuizOnlineInterview> assignedQuizzesIterator = assignedQuizzes.iterator();
		AssignedQuizOnlineInterview assignedQuiz;
		while (assignedQuizzesIterator.hasNext()) {
			assignedQuiz = assignedQuizzesIterator.next();
			if (assignedQuiz.getId().getOnlineInterviewId().equals(onlineInterviewId)) {
				assignedQuizOnlineInterviewRepository
						.deleteByOnlineInterviewId(assignedQuiz.getId().getOnlineInterviewId());

			}
		}
	}

	/**
	 * Searches for online interview by one term (candidate or his quiz)
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of online interviews contains the input term by page
	 */
	@Override
	public Page<OnlineInterview> simpleSearch(String term, Pageable pageable) {
		return assignedQuizOnlineInterviewRepository.simpleSearch(term, pageable);
	}

}
