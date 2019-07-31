package com.inther.eventplaner.controller;

import com.inther.eventplaner.domain.Event;
import com.inther.eventplaner.exception.ResourceNotFoundException;
import com.inther.eventplaner.model.UserDAO;
import com.inther.eventplaner.repository.EventRepository;
import com.inther.eventplaner.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EventController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @GetMapping("/events/{eventId}")
    public Optional<Event> getEvent(@PathVariable Integer eventId) {
        return eventRepository.findById(eventId);
    }

    @PostMapping("/events/{eventId}/participate")
    public void participate(@PathVariable Integer eventId, @RequestBody String answer ) throws JSONException {
        int currentUserId = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();

        final JSONObject jsonObject = new JSONObject(answer);
        String answerString = jsonObject.getString("answer");

        if (eventRepository.findRecordCount(eventId, currentUserId) == 0) {
            eventRepository.insertParticipationInfo(currentUserId, eventId, answerString);
        } else {
            eventRepository.updateParticipationInfo(currentUserId, eventId, answerString);
        }
    }

    @GetMapping("/events/{eventId}/participants")
    public List<UserDAO> getParticipationInfo(@PathVariable Integer eventId, @RequestBody String answer ) throws JSONException {

        final JSONObject jsonObject = new JSONObject(answer);
        String answerString = jsonObject.getString("answer");

        return userRepository.getAllParticipantsInfoForEvent(eventId, answerString);
    }


    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {

        // additionally here we set an userId for created event =>
        // value of userId is id of authenticated user, so this way we can find out who created this event
        int currentUserId = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        event.setUserId(currentUserId);

        Event newEvent = eventRepository.save(event);

        // add record in conjunction table
        eventRepository.insertParticipationInfo(currentUserId, newEvent.getId(), "coming");

        return newEvent;
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable Integer eventId, @Valid @RequestBody Event eventRequest) {
        return eventRepository.findById(eventId).map(event -> {
            event.setTitle(eventRequest.getTitle());
            event.setDescription(eventRequest.getDescription());
            event.setCategory(eventRequest.getCategory());
            event.setStartdate(eventRequest.getStartdate());
            event.setEnddate(eventRequest.getEnddate());
            event.setPrice(eventRequest.getPrice());
            event.setLocation(eventRequest.getLocation());
            event.setUserId(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
            return eventRepository.save(event);
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventId) {
        return eventRepository.findById(eventId).map(event -> {
            eventRepository.delete(event);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }
}
