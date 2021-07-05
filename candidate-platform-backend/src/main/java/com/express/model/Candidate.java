package com.express.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents candidate who's going to pass the quizzes
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "CANDIDATES")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Candidate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Candidate. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The first name of this Candidate.
	 */
	@Column
	private String firstName;

	/**
	 * The last name of this Candidate.
	 */
	@Column
	private String lastName;

	/**
	 * The email of this Candidate. This email is unique to each candidate.
	 */
	@Column(unique = true)
	@Email
	@NotNull
	private String email;

	/**
	 * The address of this Candidate.
	 */
	@Column
	private String address;

	/**
	 * The phone number of this Candidate.
	 */
	@Column
	private String phoneNumber;

	/**
	 * The birth date of this Candidate.
	 */
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	/**
	 * The graduationYear of this Candidate.
	 */
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date graduationYear;

	/**
	 * The university of this Candidate.
	 */
	@Column
	private String university;

	/**
	 * The experience of this Candidate.
	 */
	@Column
	private int experience;

	/**
	 * The availability date of this Candidate.
	 */
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date availabilityDate;

	/**
	 * The userName for the account of this Candidate. This userName is unique to
	 * each candidate.
	 */
	@Column(unique = true)
	private String username;

	/**
	 * The password for the account of this Candidate.
	 */
	@Column
	private String password;

	/**
	 * The profile of this Candidate.
	 */
	@Column
	private String profil;

	/**
	 * The status of this Candidate.
	 */
	@Column
	private String status;

	/**
	 * The account state of this Candidate.
	 */
	@Column
	private boolean activated;

	/**
	 * The comments about this Candidate.
	 */
	@Column
	private String comments;

	/**
	 * The created date of this Candidate.
	 */
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	/**
	 * The list of application of this Candidate.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "candidate")
	private List<Application> applications;

	/**
	 * The list of reclamation of this Candidate.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "candidate")
	private List<Reclamation> reclamations;

	/**
	 * Gets the ID of this Candidate.
	 * 
	 * @return this Candidate's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Candidate.
	 * 
	 * @param id This Candidate's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the first name of this Candidate.
	 * 
	 * @return this Candidate's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Changes the first name of this Candidate.
	 * 
	 * @param firstName This Candidate's new first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of this Candidate.
	 * 
	 * @return this Candidate's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Changes the last name of this Candidate.
	 * 
	 * @param lastName This Candidate's new last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email of this Candidate.
	 * 
	 * @return this Candidate's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Changes the email of this Candidate.
	 * 
	 * @param email This Candidate's new email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the address of this Candidate.
	 * 
	 * @return this Candidate's address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Changes the address of this Candidate.
	 * 
	 * @param address This Candidate's new address.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the phone number of this Candidate.
	 * 
	 * @return this Candidate's phone number.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Changes the phone number of this Candidate.
	 * 
	 * @param phoneNumber This Candidate's new phone number.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the birth date of this Candidate.
	 * 
	 * @return this Candidate's birth date.
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Changes the birth date of this Candidate.
	 * 
	 * @param birthDate This Candidate's new birth date.
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets the graduation year of this Candidate.
	 * 
	 * @return this Candidate's graduation year.
	 */
	public Date getGraduationYear() {
		return graduationYear;
	}

	/**
	 * Changes the graduation year of this Candidate.
	 * 
	 * @param graduationYear This Candidate's new graduation year.
	 */
	public void setGraduationYear(Date graduationYear) {
		this.graduationYear = graduationYear;
	}

	/**
	 * Gets the experience of this Candidate.
	 * 
	 * @return this Candidate's experience.
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * Changes the experience of this Candidate.
	 * 
	 * @param experience This Candidate's new experience.
	 */
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * Gets the availability date of this Candidate.
	 * 
	 * @return this Candidate's availability date.
	 */
	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	/**
	 * Changes the availability date of this Candidate.
	 * 
	 * @param availabilityDate This Candidate's new availability date.
	 */
	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	/**
	 * Gets the userName of this Candidate.
	 * 
	 * @return this Candidate's userName.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Changes the usernName of this Candidate.
	 * 
	 * @param username This Candidate's new userName.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password of this Candidate.
	 * 
	 * @return this Candidate's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Changes the password of this Candidate.
	 * 
	 * @param password This Candidate's new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the profile of this Candidate.
	 * 
	 * @return this Candidate's profile.
	 */
	public String getProfil() {
		return profil;
	}

	/**
	 * Changes the profile of this Candidate.
	 * 
	 * @param profil This Candidate's new profile.
	 */
	public void setProfil(String profil) {
		this.profil = profil;
	}

	/**
	 * Gets the status of this Candidate.
	 * 
	 * @return this Candidate's status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Changes the status of this Candidate.
	 * 
	 * @param status This Candidate's new status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the university of this Candidate.
	 * 
	 * @return this Candidate's university.
	 */
	public String getUniversity() {
		return university;
	}

	/**
	 * Changes the university of this Candidate.
	 * 
	 * @param university This Candidate's new university.
	 */
	public void setUniversity(String university) {
		this.university = university;
	}

	/**
	 * Gets the state of the account of this Candidate.
	 * 
	 * @return this Candidate's state of the account.
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * Changes the state of the account of this Candidate.
	 * 
	 * @param activated This Candidate's new account state.
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * Gets the comments about this Candidate.
	 * 
	 * @return this Candidate's comments.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Changes the comments of this Candidate.
	 * 
	 * @param comments This Candidate's new comments.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Gets the created date of this Candidate.
	 * 
	 * @return this Candidate's created date.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Changes the created date of this Candidate.
	 * 
	 * @param createdAt This Candidate's new created date.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the list of applications of this Candidate.
	 * 
	 * @return this Candidate's list of applications.
	 */
	public List<Application> getApplications() {
		return applications;
	}

	/**
	 * Changes the list of applications of this Candidate.
	 * 
	 * @param applications This Candidate's new list of applications.
	 */
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	/**
	 * Gets the list of reclamations of this Candidate.
	 * 
	 * @return this Candidate's list of reclamations.
	 */
	public List<Reclamation> getReclamations() {
		return reclamations;
	}

	/**
	 * Changes the list of reclamations of this Candidate.
	 * 
	 * @param reclamations This Candidate's new list of reclamations.
	 */
	public void setReclamations(List<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}

}
