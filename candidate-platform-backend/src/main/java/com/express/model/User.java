package com.express.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents users of application
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this User. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The first name of this User.
	 */
	@Column
	private String firstName;

	/**
	 * The last name of this User.
	 */
	@Column
	private String lastName;

	/**
	 * The email of this User. This email is unique to each User.
	 */
	@Column(unique = true)
	@Email
	@NotNull
	private String email;

	/**
	 * The address of this User.
	 */
	@Column
	private String address;

	/**
	 * The phone number of this User.
	 */
	@Column
	private String phoneNumber;

	/**
	 * The birth date of this User.
	 */
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	/**
	 * The userName for the account of this User. This userName is unique to each
	 * User.
	 */
	@Column
	private String username;

	/**
	 * The password for the account of this User.
	 */
	@Column
	private String password;

	/**
	 * The account state of this User.
	 */
	@Column
	private boolean activated;

	/**
	 * The role of this User.
	 * 
	 * @see Role
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_ROLE"))
	private Role role;

	/**
	 * The activity of this User.
	 * 
	 * @see Activity
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id", foreignKey = @ForeignKey(name = "FK_ACTIVITY"))
	private Activity activity;

	/**
	 * The list of created quizzes of this User.
	 * 
	 * @see Quiz
	 */
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	private List<Quiz> createdQuizzes;

	/**
	 * The list of created question of this User.
	 * 
	 * @see Role
	 */
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	private List<Question> createdQuestions;

	/**
	 * Gets the ID of this User.
	 * 
	 * @return this User's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this User.
	 * 
	 * @param id This User's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the first name of this User.
	 * 
	 * @return this User's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Changes the first name of this User.
	 * 
	 * @param firstName This User's new first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of this User.
	 * 
	 * @return this User's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Changes the last name of this User.
	 * 
	 * @param lastName This User's new last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email of this User.
	 * 
	 * @return this User's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Changes the email of this User.
	 * 
	 * @param email This User's new email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the address of this User.
	 * 
	 * @return this User's address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Changes the address of this User.
	 * 
	 * @param address This User's new address.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the phone number of this User.
	 * 
	 * @return this User's phone number.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Changes the phone number of this User.
	 * 
	 * @param phoneNumber This User's new phone number.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the birth date of this User.
	 * 
	 * @return this User's birth date.
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Changes the birth date of this User.
	 * 
	 * @param birthDate This User's new birth date.
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets the userName of this User.
	 * 
	 * @return this User's userName.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Changes the usernName of this User.
	 * 
	 * @param username This User's new userName.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password of this User.
	 * 
	 * @return this User's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Changes the password of this User.
	 * 
	 * @param password This User's new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the role of this User.
	 * 
	 * @return this User's role.
	 * @see Role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Changes the role of this User.
	 *
	 * 
	 * @param password This User's new role.
	 * @see Role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Gets the list of created quizzes of this User.
	 * 
	 * @return this User's list of created quizzes.
	 * @see Quiz
	 */
	public List<Quiz> getCreatedQuizzes() {
		return createdQuizzes;
	}

	/**
	 * Changes the list of created quizzes of this User.
	 *
	 * 
	 * @param createdQuizzes This User's new list of created quizzes.
	 * @see Quiz
	 */
	public void setCreatedQuizzes(List<Quiz> createdQuizzes) {
		this.createdQuizzes = createdQuizzes;
	}

	/**
	 * Gets the list of created questions of this User.
	 * 
	 * @return this User's list of created questions.
	 * @see Question
	 */
	public List<Question> getCreatedQuestions() {
		return createdQuestions;
	}

	/**
	 * Changes the list of created questions of this User.
	 *
	 * @param createdQuestions This User's new list of created questions.
	 * @see Question
	 */
	public void setCreatedQuestions(List<Question> createdQuestions) {
		this.createdQuestions = createdQuestions;
	}

	/**
	 * Gets the state of the account of this User.
	 * 
	 * @return this User's state of the account.
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * Changes the state of the account of this User.
	 * 
	 * @param activated This User's new account state.
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * Gets the activity of this User.
	 * 
	 * @return this User's activity.
	 * @see Activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Changes the activity of this User.
	 *
	 * 
	 * @param activity This User's new activity.
	 * @see Activity
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
