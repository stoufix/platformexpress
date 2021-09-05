package com.express.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import static com.express.util.Constants.APPLICATION_FILTER;

/**
 * Represents application of candidate to an offer
 * 

 * @version 1.0
 */
@Entity
@Table(name = "APPLICATIONS")
@JsonFilter(APPLICATION_FILTER)
public class Application implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Application. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The Date when this Application is created.
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy")
	private Date createdAt;

	/**
	 * The status of this Application.
	 */
	@Column
	private String status;

	/**
	 * The candidate of this Application.
	 * 
	 * @see Candidate
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "candidate_id", foreignKey = @ForeignKey(name = "FK_CANDIDATE"))
	private Candidate candidate;

	/**
	 * The OnlineInterview of this Application.
	 * 
	 * @see OnlineInterview
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "application")
	private OnlineInterview onlineInterview;

	/**
	 * Gets the ID of this Application.
	 * 
	 * @return this Application's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Application.
	 * 
	 * @param id This Application's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the created date of this Application.
	 * 
	 * @return this Application's created date.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Changes the created date of this Application.
	 * 
	 * @param createdAt This Application's new created date.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the status of this Application.
	 * 
	 * @return this Application's status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Changes the status of this Application.
	 * 
	 * @param status This Application's new status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the candidate of this Application.
	 * 
	 * @return this Application's candidate.
	 * @see Candidate
	 */
	public Candidate getCandidate() {
		return candidate;
	}

	/**
	 * Changes the candidate of this Application.
	 * 
	 * @param candidate This Application's new candidate.
	 * @see Candidate
	 */
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	/**
	 * Gets the onlineInterview of this Application.
	 * 
	 * @return this Application's onlineInterview.
	 * @see OnlineInterview
	 */
	public OnlineInterview getOnlineInterview() {
		return onlineInterview;
	}

	/**
	 * Changes the onlineInterview of this Application.
	 * 
	 * @param onlineInterview This Application's new onlineInterview.
	 * @see OnlineInterview
	 */
	public void setOnlineInterview(OnlineInterview onlineInterview) {
		this.onlineInterview = onlineInterview;
	}

}