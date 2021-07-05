package com.altran.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents reclamation made by candidate
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "RECLAMATIONS")
public class Reclamation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Reclamation. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The subject of this Reclamation.
	 */
	@Column
	private String subject;

	/**
	 * The body of this Reclamation
	 */
	@Column(length = 960)
	private String body;

	/**
	 * The created date of this Reclamation
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date createdAt;

	/**
	 * The consultation status of the Reclamation.
	 */
	@Column
	private boolean consulted;

	/**
	 * The candidate who sent this Reclamation
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "candidate_id", foreignKey = @ForeignKey(name = "FK_CANDIDATE"))
	private Candidate candidate;

	/**
	 * Gets the ID of this Reclamation.
	 * 
	 * @return this Reclamation's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Reclamation.
	 * 
	 * @param id This Reclamation's new ID.
	 */

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the subject of this Reclamation.
	 * 
	 * @return this Reclamation's subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Changes the subject of this Activity.
	 * 
	 * @param subject This Reclamation's new subject.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the body of this Reclamation.
	 * 
	 * @return this Reclamation's body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Changes the body of this Reclamation.
	 * 
	 * @param body This Reclamation's new body.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets the created date of this Reclamation.
	 * 
	 * @return this Reclamation's created date.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Changes the created date of this Reclamation.
	 * 
	 * @param createdAt This Reclamation's new created date.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the consultation status of this Reclamation.
	 * 
	 * @return this Reclamation's consultation status.
	 */
	public boolean isConsulted() {
		return consulted;
	}

	/**
	 * Changes the consultation status of this Reclamation.
	 * 
	 * @param consulted This Reclamation's new consultation status.
	 */
	public void setConsulted(boolean consulted) {
		this.consulted = consulted;
	}

	/**
	 * Gets the candidate of this Reclamation.
	 * 
	 * @return this Reclamation's candidate.
	 */
	public Candidate getCandidate() {
		return candidate;
	}

	/**
	 * Changes the candidate of this Reclamation.
	 * 
	 * @param candidate This Reclamation's new candidate.
	 */
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

}