package com.altran.model;

/**
 * Represents LoginUser model: contains userName and password of authenticated
 * user
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
public class LoginUser {

	private String username;
	private String password;

	/**
	 * Gets the userName of this authenticated User.
	 * 
	 * @return this authenticated User's userName.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Changes the usernName of this authenticated User.
	 * 
	 * @param username This authenticated User's new userName.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password of this authenticated user.
	 * 
	 * @return this authenticated user's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Changes the password of this authenticated user.
	 * 
	 * @param password this authenticated User's new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
