package com.altran.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.model.Quiz;

/**
 * Represents repository of quiz
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	List<Quiz> findAllByOrderByCreatedAtDesc();

	Page<Quiz> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Query("SELECT DISTINCT t FROM Quiz t where (LOWER (t.title) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') ) AND ((t.shared = true) OR ((t.shared = false) AND (t.createdBy.username = :username)))  ORDER BY t.createdAt desc")
	public Page<Quiz> simpleSearchWithoutActivity(@Param("username") String username, @Param("term") String term,
			Pageable pageable);

	@Query("SELECT DISTINCT t FROM Quiz t where (LOWER (t.title) LIKE CONCAT('%', LOWER ( :term ), '%')  "
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') ) AND (((t.shared = true) AND (t.createdBy.activity.title = :activity)) OR ((t.shared = false) AND ((t.createdBy.username = :username) AND (t.createdBy.activity.title = :activity)) )) ORDER BY t.createdAt desc")
	public Page<Quiz> simpleSearchWithActivity(@Param("username") String username, @Param("activity") String activity,
			@Param("term") String term, Pageable pageable);

	@Query("SELECT DISTINCT t FROM Quiz t where (LOWER (t.title) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER (t.createdBy.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.createdBy.username) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.technology.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.degree.title) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') ) AND  (t.createdBy.username = :username) ORDER BY t.createdAt desc")
	public Page<Quiz> simpleSearchByUser(@Param("username") String username, @Param("term") String term,
			Pageable pageable);

	@Query("SELECT DISTINCT t FROM Quiz t where ((t.technology.id in (:technologies_id))"
			+ " OR ((:technologies_id) is null)) AND ((t.degree.id in (:degrees_id))"
			+ " OR ((:degrees_id) is null))  AND ((t.activity.id in (:activities_id))"
			+ " OR ((:activities_id) is null)) AND ((t.createdBy.id in (:users_id))"
			+ " OR ((:users_id) is null)) ORDER BY t.createdAt desc ")
	public Page<Quiz> advancedSearch(@Param("technologies_id") List<Long> technologiesId,
			@Param("degrees_id") List<Long> degreesId, @Param("activities_id") List<Long> activitiesId,
			@Param("users_id") List<Long> usersId, Pageable pageable);

	@Query("SELECT DISTINCT t FROM Quiz t where ((t.technology.id in (:technologies_id))"
			+ " OR ((:technologies_id) is null)) AND ((t.degree.id in (:degrees_id))"
			+ " OR ((:degrees_id) is null))  AND ((t.activity.id in (:activities_id))"
			+ " OR ((:activities_id) is null)) AND ((t.createdBy.id in (:users_id))"
			+ " OR ((:users_id) is null)) AND (((t.shared = true) AND (t.createdBy.activity.title = :activity)) OR ((t.shared = false) AND (t.createdBy.username = :username) )) ORDER BY t.createdAt desc")
	public Page<Quiz> advancedSearchWithActivity(@Param("technologies_id") List<Long> technologiesId,
			@Param("degrees_id") List<Long> degreesId, @Param("activities_id") List<Long> activitiesId,
			@Param("users_id") List<Long> usersId, @Param("username") String username,
			@Param("activity") String activity, Pageable pageable);

	@Query("SELECT DISTINCT t FROM Quiz t where ((t.technology.id in (:technologies_id))"
			+ " OR ((:technologies_id) is null)) AND ((t.degree.id in (:degrees_id))"
			+ " OR ((:degrees_id) is null)) AND  (t.createdBy.username = :username) ORDER BY t.createdAt desc ")
	public Page<Quiz> advancedSearchByUser(@Param("technologies_id") List<Long> technologiesId,
			@Param("degrees_id") List<Long> degreesId, @Param("username") String username, Pageable pageable);

}
