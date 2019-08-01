package com.inther.eventplaner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inther.eventplaner.domain.Event;
import com.inther.eventplaner.domain.EventUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class UserDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column
	private String username;


	@Column
	@JsonIgnore
	private String password;

	@Column
	@NotNull
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

	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private Set<EventUser> participants  = new HashSet<>();

}