package com.inther.eventplaner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inther.eventplaner.domain.Event;
import com.inther.eventplaner.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.HashSet;
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

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = {CascadeType.ALL}
	)
	@JoinTable(
			name = "user_event",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "event_id")}
	)
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	private Set<Event> events = new HashSet<>();

	/*@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;*/

}