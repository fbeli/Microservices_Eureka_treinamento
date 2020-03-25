package br.com.becb.api.users.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

	@NotNull(message = "coloca um nome ai")
	private String firstName;

	
	@Size(min = 3, message = "coloca algo maior que 3, que tal?")
	private String lastName;

	@Email	
	private String email;
	private String userId;
	@NotNull
	@Size(min = 6, message = "coloca algo maior que 6, que tal?")
	private String password;

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
