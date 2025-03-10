package com.example.EventService.controller;

import com.example.EventService.entity.EventPlan;
import com.example.EventService.service.EventPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-plans")
public class EventPlanController {

    private final EventPlanService eventPlanService;

    public EventPlanController(EventPlanService eventPlanService) {
        this.eventPlanService = eventPlanService;
    }

    @PostMapping
    public ResponseEntity<EventPlan> createEventPlan(
            @RequestBody EventPlan eventPlan
    ) {

        EventPlan createdEventPlan = eventPlanService.createEventPlan(eventPlan);
        return ResponseEntity.ok(createdEventPlan);
    }

    @GetMapping
    public ResponseEntity<List<EventPlan>> getAllEventPlans() {

        List<EventPlan> eventPlans = eventPlanService.getAllEventPlans();
        return ResponseEntity.ok(eventPlans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventPlan> getEventPlanById(
            @PathVariable Long id
    ) {


        return eventPlanService.getEventPlanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('client_admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventPlan(
            @PathVariable Long id
    ) {

        eventPlanService.deleteEventPlan(id);
        return ResponseEntity.noContent().build();
    }
}
