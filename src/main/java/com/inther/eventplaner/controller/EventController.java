package com.inther.eventplaner.controller;

import com.inther.eventplaner.domain.Event;
import com.inther.eventplaner.exception.ResourceNotFoundException;
import com.inther.eventplaner.model.UserDAO;
import com.inther.eventplaner.repository.EventRepository;
import com.inther.eventplaner.repository.UserRepository;
import com.inther.eventplaner.service.EmailSenderService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private EmailSenderService emailSenderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(PageRequest.of(0, (int) eventRepository.count()));
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

        String organizerEmail = userRepository.findById(eventRepository.getOrganizerIdOfEvent(eventId)).get().getEmail();

        // send info email to organizer about user participation
        emailSenderService.sendEmail(organizerEmail,
                "New participation info for event #" + eventId,
                getEventDetailsForEmail(eventId) +
                        getParticipationInfoForEmail(currentUserId, answerString));
    }

    @PostMapping("/events/{eventId}/participants")
    public List<UserDAO> getParticipationInfo(@PathVariable Integer eventId, @RequestBody String answer ) throws JSONException {

        final JSONObject jsonObject = new JSONObject(answer);
        String answerString = jsonObject.getString("answer");

        return userRepository.getAllParticipantsInfoForEvent(eventId, answerString);
    }


    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {

        // additionally here we set an userId for created event =>
        // value of userId is id of authenticated user, so later we can find out who is the organizer of this event
        int currentUserId = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        event.setUserId(currentUserId);

        Event newEvent = eventRepository.save(event);

        // add record in conjunction table
        eventRepository.insertParticipationInfo(currentUserId, newEvent.getId(), "coming");

        // send email notification to all registered users
        for (String userEmail: userRepository.getAllUserEmails()
        ) {
            emailSenderService.sendEmail(userEmail.trim(),
                    "New Event",
                    getEventDetailsForEmail(newEvent.getId()));
        }

        return newEvent;
    }

    private String getEventDetailsForEmail(int eventId) {

        Event event = eventRepository.findById(eventId);
        StringBuilder emailText = new StringBuilder("Event Details\n");
        emailText.append("Event Title: " + event.getTitle());
        emailText.append("\nStart Date: " + event.getStartdate().toGMTString());
        emailText.append("\nDescription: " + event.getDescription());

        return emailText.toString();
    }

    private String getParticipationInfoForEmail (int userId, String answer) {
        return "\nUser: " + userRepository.findById(userId).get().getFirstname() + " "
                + userRepository.findById(userId).get().getLastname()
        + "\nParticipation: " + answer;
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
            eventRepository.save(event);

            // send email to participants of event with notification event details modified
            for (String userEmail: eventRepository.getAllParticipantsEmailsOfEvent(eventId)
            ) {
                emailSenderService.sendEmail(userEmail.trim(),
                        "Event #" + eventId + " modified",
                        getEventDetailsForEmail(eventId));
            }

            return event;
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventId) {
        return eventRepository.findById(eventId).map(event -> {
            List<String> eventParticipants = eventRepository.getAllParticipantsEmailsOfEvent(eventId);
            String eventDetails = getEventDetailsForEmail(eventId);
            eventRepository.delete(event);
            // send email to participants with notification event deleted
            for (String userEmail: eventParticipants
            ) {
                emailSenderService.sendEmail(userEmail.trim(),
                        "Event #" + eventId + " deleted",
                        eventDetails);
            }
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("EventId " + eventId + " not found"));
    }
}
