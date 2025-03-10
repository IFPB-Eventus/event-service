package com.example.EventService.service;

import com.example.EventService.entity.EventPlan;
import com.example.EventService.repository.EventPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventPlanService {

    private final EventPlanRepository eventPlanRepository;

    public EventPlanService(EventPlanRepository eventPlanRepository) {
        this.eventPlanRepository = eventPlanRepository;
    }

    public EventPlan createEventPlan(EventPlan eventPlan) {
        return eventPlanRepository.save(eventPlan);
    }

    public List<EventPlan> getAllEventPlans() {
        return eventPlanRepository.findAll();
    }

    public Optional<EventPlan> getEventPlanById(Long id) {
        return eventPlanRepository.findById(id);
    }

    public void deleteEventPlan(Long id) {
        eventPlanRepository.deleteById(id);
    }
}