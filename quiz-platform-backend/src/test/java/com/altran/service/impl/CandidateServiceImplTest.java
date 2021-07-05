package com.altran.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.altran.model.Candidate;
import com.altran.repository.CandidateRepository;
import com.altran.run.QuizPlatformBackendApplication;
import com.altran.service.CandidateService;

/**
 * Represents the test of candidate service
 * 
 * @author Oumaima.Meskini
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { QuizPlatformBackendApplication.class })
public class CandidateServiceImplTest {

	@MockBean()
	private CandidateRepository candidateRepository;

	@Autowired
	private CandidateService candidateService;

	/*
	 * This class is testing the behavior of the method getById when there is no
	 * exception thrown
	 */
	@Test
	public void getByIdTest_when_present_expect_noException() {

		// Mocking the CandidateRepository behavior
		Mockito.when(candidateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Candidate()));

		// call the method findById of the service
		Candidate found = candidateService.findById(new Long(1));
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method getById when No Exception is
	 * thrown
	 */
	@Test(expected = NoSuchElementException.class)
	public void getByIdTest_when_notPresent_expect_throwException() {

		// Mocking the CandidateRepository behavior
		Mockito.when(candidateRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		// call the method findById of the service
		candidateService.findById(new Long(1200));
	}

	/*
	 * This class is testing the behavior of the method findAll when there is no
	 * exception thrown
	 */
	@Test
	public void findAllTest() {

		Mockito.when(candidateRepository.findAll()).thenReturn(new ArrayList<Candidate>());

		List<Candidate> found = candidateService.findAll();

		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method Update when there is no
	 * exception thrown
	 */
	@Test
	public void updateTest() {

		Mockito.when(candidateRepository.existsById(Mockito.anyLong())).thenReturn(true);

		Mockito.when(candidateRepository.save(Mockito.any(Candidate.class))).thenReturn(new Candidate());

		Candidate found = candidateService.update(new Candidate(), new Long(1));
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method simpleUpdate when a null Id
	 * is passed into the parameters of the method
	 */
	@Test
	public void updateTest_NullId() {

		Mockito.when(candidateRepository.existsById(Mockito.anyLong())).thenReturn(false);
		Mockito.when(candidateRepository.save(Mockito.any(Candidate.class))).thenReturn(new Candidate());

		Candidate found = candidateService.update(new Candidate(), null);
		assertNotNull(found);
	}

	/*
	 * This class is testing the behavior of the method simpleUpdate when Entity not
	 * found Exception is thrown
	 */
	@Test(expected = EntityNotFoundException.class)
	public void updateTest_when_notPresent_expect_throwException() {
		Mockito.when(candidateRepository.existsById(Mockito.anyLong())).thenReturn(false);

		candidateService.update(new Candidate(), new Long(1));

	}
}
