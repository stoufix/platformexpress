package com.altran.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import com.altran.model.Question;
import com.altran.model.Technology;
import com.altran.model.User;
import com.altran.model.Degree;
import com.altran.model.Proposition;
import com.altran.repository.QuestionRepository;
import com.altran.run.QuizPlatformBackendApplication;
import com.altran.service.DegreeService;
import com.altran.service.PropositionService;
import com.altran.service.QuestionService;
import com.altran.service.TechnologyService;
import com.altran.service.UserService;


/**
 * Represents the test of question service
 * 
 * @author Oumaima.Meskini
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { QuizPlatformBackendApplication.class })
public class QuestionServiceImplTest {

	@MockBean()
	private QuestionRepository questionRepository;

	@MockBean()
	private UserDetailsService userService;

	@MockBean()
	private UserService userService2;

	@MockBean()
	private DegreeService degreeService;
	
	@MockBean()
	private TechnologyService technologyService;
	
	@MockBean()
	private PropositionService propositionService;

	@MockBean()

	private Question question;
	
	@Autowired
	private QuestionService questionService;


	/*
	 * This class is testing the behavior of the method getById 
	 * when there is no exception thrown
	 */
	@Test
	public void getByIdTest_when_present_expect_noException() {

		//Mocking the QuestionRepository behavior
		Mockito.when(questionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Question()));
        
		//call the method findById of the service
		Question found = questionService.findById(new Long(1));
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method getById 
	 * when No  Exception is thrown
	 */
	@Test(expected = NoSuchElementException.class)
	public void getByIdTest_when_notPresent_expect_throwException() {
		
		//Mocking the QuestionRepository behavior
		Mockito.when(questionRepository.findById(Mockito.anyLong())).thenReturn(null);

		//call the method findById of the service
		questionService.findById(new Long(11));

	}

	/*
	 * This class is testing the behavior of the method findAll
	 * when there is no exception thrown
	 */
	@Test
	public void findAllTest() {

		Mockito.when(questionRepository.findAll()).thenReturn(new ArrayList<Question>());

		List<Question> found = questionService.findAll();

		assertNotNull(found);
	}

	
	/*
	 * This class is testing the behavior of the method findAllByPage
	 * when there is no exception thrown
	 */
	@Test
	public void findAllByPageTest() {
	
		List<Question> expected = new ArrayList<Question>();
		Page<Question> expectedPage = new PageImpl<Question>(expected);

		Mockito.when(questionRepository.findAll(Mockito.any(Pageable.class))).thenReturn(expectedPage);
		Page<Question> found = questionService.findAllByPage(PageRequest.of(1, 50));

		assertNotNull(found.getSize());
	}

	/*
	 * This class is testing the behavior of the method findAllVisibleQuestion
	 * when there is no exception thrown
	 */
	@Test
	public void findAllVisibleQuestionsTest() {

		User expected_user = (User) userService2.findByUsername("moez");

		Mockito.when((userService2).findByUsername(Mockito.anyString())).thenReturn(expected_user);

		Page<Question> found = questionService.findAllVisibleQuestions("moez", PageRequest.of(1, 50));

		assertNotNull(found.getSize());

	}


	/*
	 * This class is testing the behavior of the method SimpleSearchWithoutActivity
	 * when there is no exception thrown
	 */
	@Test
	public void SimpleSearchWithoutActivityTest() {

		Mockito.when(questionRepository.simpleSearchWithoutActivity(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Pageable.class))).thenReturn(new PageImpl<Question>(new ArrayList<Question>()));
		Page<Question> found = questionService.simpleSearchWithoutActivity("ahmed", "ahmed", PageRequest.of(1, 50));

		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method SimpleSearchByUser
	 * when there is no exception thrown
	 */
	@Test
	public void SimpleSearchByUser() {
		Mockito.when(questionRepository.simpleSearchByUser(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Pageable.class))).thenReturn(new PageImpl<Question>(new ArrayList<Question>()));
		Page<Question> found = questionService.simpleSearchByUser("ahmed", "ahmed", PageRequest.of(1, 50));
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method simpleUpdate
	 * when there is no exception thrown
	 */
	@Test
	public void simpleUpdateTest() {
		Mockito.when(questionRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(new Question());

		Question found = questionService.simpleUpdate(new Long(1), new Question());
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method simpleUpdate
	 * when Entity not found Exception is thrown
	 */
	@Test(expected = EntityNotFoundException.class)
	public void simpleUpdateTest_expect_Exception() {

		Mockito.when(questionRepository.existsById(Mockito.anyLong())).thenReturn(false);

		questionService.simpleUpdate(new Long(1), new Question());
	}

	/*
	 * This class is testing the behavior of the method simpleUpdate
	 * when a null Id is passed into the parameters of the method
	 */
	@Test
	public void simpleUpdateTest_NullId() {

		Mockito.when(questionRepository.existsById(Mockito.anyLong())).thenReturn(false);
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(new Question());

		Question found = questionService.simpleUpdate(null, new Question());
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method create
	 * when Entity Exists Exception is thrown
	 */
	@Test(expected = EntityExistsException.class)
	public void createTest_expect_Exception() {

		Question question = new Question();
		question.setId(1L);

		Mockito.when(questionRepository.existsById(Mockito.anyLong())).thenReturn(true);

		questionService.create(question);
	}
	/*
	 * This class is testing the behavior of the method create
	 * when there is no exception thrown
	 */
	@Test
	public void createTest() {
		Question question = new Question();
		question.setId(1L);
		question.setPropositions(new HashSet<Proposition>());
		Technology technology = new Technology();
		technology.setId(1L);
		question.setTechnology(technology);
		Degree degree = new Degree();
		degree.setId(1L);
		question.setTechnology(technology);
		question.setDegree(degree);
		Mockito.when(questionRepository.existsById(Mockito.anyLong())).thenReturn(false);
		Mockito.when(technologyService.findById(Mockito.anyLong())).thenReturn(new Technology());
		Mockito.when(degreeService.findById(Mockito.anyLong())).thenReturn(degree);
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(new Question());
		Mockito.when(propositionService.create(Mockito.any(Proposition.class))).thenReturn(new Proposition());
		//questionService.create(question);

	}
}
