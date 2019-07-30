package com.inther.eventplaner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Title is required!")
    private String title;

    private String description;

    private String category;

    @NotNull(message = "Introduce Start Date/Time of event start")
    private Date startdate;

    private Date enddate;
    private float price;
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<EventUser> participants  = new HashSet<>();

    private int userId;

}
