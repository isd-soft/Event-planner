package com.inther.eventplaner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inther.eventplaner.model.UserDAO;
import lombok.Data;
import lombok.EqualsAndHashCode;
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


    @Column(nullable = true)
    private Date enddate;

    private float price;
    private String location;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "events"
    )
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<UserDAO> users = new HashSet<>();

    @JsonIgnore
    @Column
    private int userId;

//    private Collection<UserDAO> participants = new ArrayList<>();
//    private ArrayList<UserDAO> organizers;

}
