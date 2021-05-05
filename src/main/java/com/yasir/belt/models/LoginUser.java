package com.yasir.belt.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginUser {
	
//	notice how we are not giving an at entity or at table because we are not saving it to the db
	public LoginUser() {} //constructor
	
	@Email(message = "Invalid Email")
	@NotEmpty(message = "email is required")
	private String email;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// these are the only two fields we care about for login and reg
	@NotEmpty(message = "password is required")
	@Size(min = 8, message = "password must be 8 characters or longer")
	private String password;
}
