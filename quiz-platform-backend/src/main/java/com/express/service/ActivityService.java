package com.express.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.express.model.Activity;

/**
 * Represents the interface of activity service
 * @version 1.0
 */
public interface ActivityService {

	List<Activity> findAll();

	Page<Activity> findAllByPage(Pageable pageable);

	Activity findById(Long id);

	Activity findByTitle(String title);

	Page<Activity> simpleSearch(String term, Pageable pageable);

	Activity create(Activity activity);

	Activity update(Activity activity, Long id);

	void delete(Long id);

}