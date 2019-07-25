package com.inther.eventplaner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inther.eventplaner.model.UserDAO;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

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
    private ArrayList<UserDAO> participants;
    private ArrayList<UserDAO> organizers;

}
