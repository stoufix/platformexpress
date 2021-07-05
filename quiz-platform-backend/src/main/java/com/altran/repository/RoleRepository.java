package com.altran.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altran.model.Role;

/**
 * Represents repository of role
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findAllByOrderByTitleAsc();

	Page<Role> findAllByOrderByTitleAsc(Pageable pageable);

	Role findByTitle(String title);

	@Query("SELECT t FROM Role t where LOWER (t.title) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Role> simpleSearch(@Param("term") String title, Pageable pageable);
}