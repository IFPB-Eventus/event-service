package com.example.EventService.controller;

import com.example.EventService.entity.EventRegistration;
import com.example.EventService.service.EventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        String userId = authentication.getName(); // Obtém ID do usuário autenticado
        EventRegistration createdRegistration = eventRegistrationService.registerUserToEvent(userId, eventId);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventRegistration(@PathVariable Long id) {
        eventRegistrationService.deleteEventRegistration(id);
        return ResponseEntity.noContent().build();
    }
}