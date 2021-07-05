package com.altran.model.filter;

import java.util.List;

import com.altran.model.Activity;
import com.altran.model.Degree;
import com.altran.model.Technology;
import com.altran.model.User;

public class QuestionFilter {

	private List<Technology> technologies;

	private List<Degree> degrees;

	private List<Activity> activities;

	private List<User> names;

	public List<Degree> getDegrees() {
		return degrees;
	}

	public void setDegrees(List<Degree> degrees) {
		this.degrees = degrees;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activity) {
		this.activities = activity;
	}

	public List<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}

	public List<User> getNames() {
		return names;
	}

	public void setNames(List<User> users) {
		this.names = users;
	}

}
