package com.inther.eventplaner.controller;

import com.inther.eventplaner.domain.Event;
import com.inther.eventplaner.exception.ResourceNotFoundException;
import com.inther.eventplaner.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
public class EventController {

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
