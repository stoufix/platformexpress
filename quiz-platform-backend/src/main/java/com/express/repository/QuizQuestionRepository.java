package com.express.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.Question;
import com.express.model.QuizQuestion;
import com.express.model.QuizQuestionId;

/**
 * Represents repository of quizQuetion
 * 
 Maha.BSaid
 * @version 1.0
 */
@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

	Optional<QuizQuestion> findById(QuizQuestionId id);

	void deleteById(QuizQuestionId id);

	List<QuizQuestion> findByQuestion(Question question);

}
