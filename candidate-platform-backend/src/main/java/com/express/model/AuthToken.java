package com.express.model;

/**
 * Represents the token generated when authenticating
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
public class AuthToken {

	private String token;

	public AuthToken() {
	}

	/**
	 * Constructs token of authenticated user
	 * 
	 * @param token the token of authenticated user
	 */
	public AuthToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the token of this authenticated user
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Changes the token of this authenticated user
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
