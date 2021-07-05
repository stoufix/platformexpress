package com.altran.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.model.User;

/**
 * Represents repository of user
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByOrderByFirstNameAsc();

	User findByUsername(String username);

	@Query("SELECT t FROM User t where ( LOWER ( t.firstName ) LIKE CONCAT('%', LOWER ( :term ), '%')  "
			+ "OR LOWER ( t.lastName ) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER ( t.email ) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER ( t.address ) LIKE CONCAT('%', LOWER ( :term ), '%') OR t.phoneNumber  LIKE CONCAT('%', :term , '%') "
			+ "OR LOWER ( t.role.title ) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER (CONCAT(t.lastName,' ',t.firstName)) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER (CONCAT(t.firstName,' ',t.lastName)) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER ( t.activity.title ) LIKE CONCAT('%', LOWER ( :term ), '%') ) AND (t.username <> :authUsername )")
	public Page<User> simpleSearch(@Param("authUsername") String username, @Param("term") String term,
			Pageable pageable);
}
