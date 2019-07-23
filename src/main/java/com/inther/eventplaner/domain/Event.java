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

    @NotNull(message = "Description is required!")
    private String description;

    @NotNull(message = "Event category should be specified")
    private String category;

    @NotNull(message = "Introduce Date/Time of event start")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;

    //@NotNull(message = "How long will be this event?")
    private Duration duration;

    private float price;
    private String location;
    private ArrayList<UserDAO> participants;
    private ArrayList<UserDAO> organizers;

}
