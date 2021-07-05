package com.altran.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.model.Question;

/**
 * Represents repository of question
 * 
 * @author Amal.Smaoui
 * @version 1.0
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findAllByOrderByCreatedAtDesc();

	Page<Question> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Query("SELECT t FROM Question t where (LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%')  "
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.activity.title) LIKE CONCAT('%', LOWER ( :term ), '%')) AND ((t.shared = true) OR ((t.shared = false) AND (t.createdBy.username = :username)))  ORDER BY t.createdAt desc")
	public Page<Question> simpleSearchWithoutActivity(@Param("username") String username, @Param("term") String term,
			Pageable pageable);

	@Query("SELECT t FROM Question t where (LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%')  "
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.activity.title) LIKE CONCAT('%', LOWER ( :term ), '%')) AND (((t.shared = true) AND (t.createdBy.activity.title = :activity)) OR ((t.shared = false) AND ((t.createdBy.username = :username) AND (t.createdBy.activity.title = :activity)) )) ORDER BY t.createdAt desc")
	public Page<Question> simpleSearchWithActivity(@Param("username") String username,
			@Param("activity") String activity, @Param("term") String term, Pageable pageable);

	@Query("SELECT t FROM Question t where (LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.activity.title) LIKE CONCAT('%', LOWER ( :term ), '%')) AND ((t.shared = true) OR ((t.shared = false) AND (t.createdBy.username = :username))) AND t.technology.id = :technology  AND t.degree.id = :degree   ORDER BY t.createdAt desc")
	public Page<Question> simpleSearchWithoutActivityWithTechnologyAndDegree(@Param("username") String username,
			@Param("term") String term, @Param("technology") Long technology, @Param("degree") Long degree,
			Pageable pageable);

	@Query("SELECT t FROM Question t where (LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%')  "
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.activity.title) LIKE CONCAT('%', LOWER ( :term ), '%')) AND (((t.shared = true) AND (t.createdBy.activity.title = :activity)) OR ((t.shared = false) AND ((t.createdBy.username = :username) AND (t.createdBy.activity.title = :activity)) )) AND t.technology.id = :technology  AND t.degree.id = :degree ORDER BY t.createdAt desc")
	public Page<Question> simpleSearchWithActivityTechnologyAndDegree(@Param("username") String username,
			@Param("activity") String activity, @Param("term") String term, @Param("technology") Long technology,
			@Param("degree") Long degree, Pageable pageable);

	@Query("SELECT t FROM Question t where (LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%')  "
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.activity.title) LIKE CONCAT('%', LOWER ( :term ), '%')) AND  (t.createdBy.username = :username) ORDER BY t.createdAt desc")
	public Page<Question> simpleSearchByUser(@Param("username") String username, @Param("term") String term,
			Pageable pageable);

	@Query("SELECT t FROM Question t where (( t.technology.id in (:technologies_id))"
			+ " OR ((:technologies_id) is null)) AND ((t.degree.id in (:degrees_id))"
			+ " OR ((:degrees_id) is null))  AND ((t.activity.id in (:activities_id))"
			+ " OR ((:activities_id) is null)) AND ((t.createdBy.id in (:users_id))"
			+ " OR ((:users_id) is null)) AND (t.shared = true) ORDER BY t.createdAt desc") 
	public Page<Question> advancedSearch(@Param("technologies_id") List<Long> technologiesId,
			@Param("degrees_id") List<Long> degreesId, @Param("activities_id") List<Long> activitiesId,
			@Param("users_id") List<Long> usersId, Pageable pageable);
	
	
	
	
	
	
	
	@Query("SELECT t FROM Question t where ((( t.technology.id in (:technologies_id))"
			+ " OR ((:technologies_id) is null)) AND ((t.degree.id in (:degrees_id))"
			+ " OR ((:degrees_id) is null))  AND ((t.activity.id in (:activities_id))"
			+ " OR ((:activities_id) is null)) AND ((t.createdBy.id in (:users_id))"
			+ " OR ((:users_id) is null))) AND (((t.shared = true) AND (t.createdBy.activity.title = :activity)) OR ((t.shared = false) AND (t.createdBy.username = :username) )) ORDER BY t.createdAt desc") 
	public Page<Question> advancedSearchWithActivity(@Param("technologies_id") List<Long> technologiesId,
			@Param("degrees_id") List<Long> degreesId, @Param("activities_id") List<Long> activitiesId,
			@Param("users_id") List<Long> usersId, @Param("username") String username,
			@Param("activity") String activity, Pageable pageable);
	
	
	
	

	@Query("SELECT t FROM Question t where (( t.technology.id in (:technologies_id))"
			+ " OR ((:technologies_id) is null)) AND ((t.degree.id in (:degrees_id))"
			+ " OR ((:degrees_id) is null)) AND  (t.createdBy.username = :username) ORDER BY t.createdAt desc")
	public Page<Question> advancedSearchByUser(@Param("technologies_id") List<Long> technologiesId,
			@Param("degrees_id") List<Long> degreesId, @Param("username") String username, Pageable pageable);

}