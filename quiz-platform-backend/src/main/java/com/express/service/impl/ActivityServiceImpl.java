package com.express.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.express.model.Activity;
import com.express.repository.ActivityRepository;
import com.express.service.ActivityService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of activity service
 * 
 Ghassen.Soussi
 * @version 1.0
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	private final ActivityRepository activityRepository;

	/**
	 * Constructor of ActivityServiceImpl
	 * 
	 * @param activityRepository the repository of activity
	 */
	@Autowired
	public ActivityServiceImpl(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	/**
	 * Gets the list of all activities sorted by title
	 * 
	 * @return list of all activities sorted by title
	 */
	@Override
	public List<Activity> findAll() {
		return activityRepository.findAllByOrderByTitleAsc();
	}

	/**
	 * Gets the list of all activities by page and sorted by title
	 * 
	 * @return list of all activities by page and sorted by title
	 */
	public Page<Activity> findAllByPage(Pageable pageable) {
		return activityRepository.findAllByOrderByTitleAsc(pageable);
	}

	/**
	 * gets one activity by his id
	 * 
	 * @param id the id of the activity
	 * @return activity object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Activity findById(Long id) {
		Optional<Activity> activity = activityRepository.findById(id);
		if (activity.isPresent())
			return activity.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Gets one activity by his name
	 * 
	 * @param name the name of the activity
	 * @return activity object with the same name
	 */
	@Override
	public Activity findByTitle(String title) {
		return activityRepository.findByTitle(title);
	}

	/**
	 * Creates a new activity
	 * 
	 * @param activity the activity to create
	 * @return the created activity
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Activity create(Activity activity) {
		if (activity.getId() != null && activityRepository.existsById(activity.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		return activityRepository.save(activity);
	}

	/**
	 * Updates one activity
	 * 
	 * @param id       the id of the activity
	 * @param activity the new activity object with the new values
	 * @return the updated activity
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Activity update(Activity activity, Long id) {
		if (id != null && !activityRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		activity.setId(id);
		return activityRepository.save(activity);
	}

	/**
	 * Deletes one activity
	 * 
	 * @param id the of the deleted activity
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !activityRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		activityRepository.deleteById(id);
	}

	/**
	 * Searches for activities by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of activities contains the input term by page
	 */
	@Override
	public Page<Activity> simpleSearch(String term, Pageable pageable) {
		return activityRepository.simpleSearch(term, pageable);
	}

}
