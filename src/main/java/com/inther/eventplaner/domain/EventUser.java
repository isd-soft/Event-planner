package com.inther.eventplaner.domain;

import com.inther.eventplaner.model.UserDAO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class EventUser implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn
    private UserDAO user;

    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventUser)) return false;
        EventUser that = (EventUser) o;
        return Objects.equals(event.getTitle(), that.event.getTitle()) &&
                Objects.equals(user.getUsername(), that.user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(event.getTitle(), user.getUsername());
    }
}
