package com.express.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.express.model.QuizQuestion;
import com.express.model.QuizQuestionId;
import com.express.repository.QuizQuestionRepository;
import com.express.run.QuizPlatformBackendApplication;
import com.express.service.QuizQuestionService;

/**
 * Represents the test of quiz_question service
 * 
 Oumaima.Meskini
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { QuizPlatformBackendApplication.class })
public class QuizQuestionServiceImplTest {

	@MockBean()
	private QuizQuestionRepository quizQuestionRepository;

	@Autowired
	private QuizQuestionService quizQuestionService;

	/*
	 * This class is testing the behavior of the method getById when there is no
	 * exception thrown
	 */
	@Test
	public void getByIdTest_when_present_expect_noException() {

		// Mocking the QuizQuestionRepository behavior
		Mockito.when(quizQuestionRepository.findById(Mockito.any(QuizQuestionId.class)))
				.thenReturn(Optional.of(new QuizQuestion()));

		// call the method findById of the service
		QuizQuestion found = quizQuestionService.findById( new QuizQuestionId());
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method getById when No Exception is
	 * thrown
	 */
	@Test(expected = NoSuchElementException.class)
	public void getByIdTest_when_notPresent_expect_throwException() {

		// Mocking the QuestionRepository behavior
		Mockito.when(quizQuestionRepository.findById(Mockito.any(QuizQuestionId.class))).thenReturn(Optional.empty());

		// call the method findById of the service
		quizQuestionService.findById(new QuizQuestionId());

		// verify that the method findById has been called
		verify(quizQuestionRepository, times(1)).findById(1L);
		verifyNoMoreInteractions(quizQuestionRepository);
	}

	/*
	 * This class is testing the behavior of the method findAll when there is no
	 * exception thrown
	 */
	@Test
	public void findAllTest() {

		Mockito.when(quizQuestionRepository.findAll()).thenReturn(new ArrayList<QuizQuestion>());

		List<QuizQuestion> found = quizQuestionService.findAll();

		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method create when there is no
	 * Exception thrown
	 */
	@Test
	public void createTest() {

		Mockito.when(quizQuestionRepository.save(Mockito.any(QuizQuestion.class))).thenReturn(new QuizQuestion());

		QuizQuestion found = quizQuestionService.create(new QuizQuestion());
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method Update when there is no
	 * exception thrown
	 */
	@Test
	public void updateTest() {

		Mockito.when(quizQuestionRepository.save(Mockito.any(QuizQuestion.class))).thenReturn(new QuizQuestion());

		QuizQuestion found = quizQuestionService.update(new Long(1), new Long(1), new QuizQuestion());
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method delete when there is no
	 * exception thrown
	 */
	@Test
	public void deleteTest() {

		Mockito.when(quizQuestionRepository.findById(Mockito.any(QuizQuestionId.class)))
				.thenReturn(Optional.of(new QuizQuestion()));

		// do nothing when the void method delete is called with Mockito
		Mockito.doNothing().when(quizQuestionRepository).delete(Mockito.any(QuizQuestion.class));
		quizQuestionService.delete(new QuizQuestionId());

		// verify that the delete method was called once a time
		verify(quizQuestionRepository, times(1)).delete(Mockito.any(QuizQuestion.class));

	}
}
