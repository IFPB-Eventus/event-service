package com.example.EventService.controller;

import com.example.EventService.entity.Event;
import com.example.EventService.entity.EventRegistration;
import com.example.EventService.service.EventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-registrations")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    @PostMapping("/{eventId}/register")
    public ResponseEntity<EventRegistration> registerUserToEvent(@PathVariable Long eventId, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String userId = jwt.getSubject(); // Obtém o ID do usuário
        String userName = jwt.getClaimAsString("preferred_username"); // Obtém o nome do usuário

        EventRegistration createdRegistration = eventRegistrationService.registerUserToEvent(userId, userName, eventId);
        return ResponseEntity.ok(createdRegistration);
    }

    @GetMapping
    public ResponseEntity<List<EventRegistration>> getAllEventRegistrations() {
        List<EventRegistration> registrations = eventRegistrationService.getAllEventRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @PreAuthorize("hasRole('client_admin')")
    @GetMapping("/{id}")
    public ResponseEntity<EventRegistration> getEventRegistrationById(@PathVariable Long id) {
        return eventRegistrationService.getEventRegistrationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> unregisterUserFromEvent(@PathVariable Long eventId, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();

        eventRegistrationService.unregisterUserFromEvent(userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<Event>> getUserRegisteredEvents(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();

        List<Event> events = eventRegistrationService.getUserRegisteredEvents(userId);
        return ResponseEntity.ok(events);
    }
}