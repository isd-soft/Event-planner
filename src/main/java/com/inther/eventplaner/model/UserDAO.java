package com.inther.eventplaner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "users_test")
public class UserDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String username;

	@Column
	@JsonIgnore
	private String password;

	@Column
	private String email;

	@Column
	private String firstname;

	@Column
	private String secondname;

	@Column
	private String phoneNumber;

	@Column
	private String photo;

	@Column
	private String description;

	@Column
	private String gender;

}