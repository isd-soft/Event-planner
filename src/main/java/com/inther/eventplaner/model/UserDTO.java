package com.inther.eventplaner.model;

import lombok.Data;

@Data
public class UserDTO {
	private int id;
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	/*private String phoneNumber;
	private String photo;
	private String description;
	private String gender;*/
}