package com.altran.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.model.Question;
import com.altran.model.QuizQuestion;
import com.altran.model.QuizQuestionId;

/**
 * Represents repository of quizQuetion
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

	Optional<QuizQuestion> findById(QuizQuestionId id);

	void deleteById(QuizQuestionId id);

	List<QuizQuestion> findByQuestion(Question question);

}
