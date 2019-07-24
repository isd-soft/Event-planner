package com.inther.eventplaner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inther.eventplaner.domain.Role;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

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
	private String lastname;

	@Column
	private String phoneNumber;

	@Column
	private String photo;

	@Column
	private String description;

	@Column
	private String gender;

	/*@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;*/

}