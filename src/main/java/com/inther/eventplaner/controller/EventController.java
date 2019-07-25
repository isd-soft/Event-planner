package com.inther.eventplaner.controller;

import com.inther.eventplaner.domain.Event;
import com.inther.eventplaner.exception.ResourceNotFoundException;
import com.inther.eventplaner.repository.EventRepository;
import com.inther.eventplaner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {
        // additionally here we set an userId for created event =>
        // value of userId is id of authenticated user, so this way we can find out who created this event
        event.setUserId(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        // and now we add this event in our set of events, so in conjunction table will be a record: user_id | event_id
        userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getEvents().add(event);
        return eventRepository.save(event);
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
