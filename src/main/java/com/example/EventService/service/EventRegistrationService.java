package com.example.EventService.service;

import com.example.EventService.entity.Event;
import com.example.EventService.entity.EventRegistration;
import com.example.EventService.repository.EventRegistrationRepository;
import com.example.EventService.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository, EventRepository eventRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventRepository = eventRepository;
    }

    public EventRegistration registerUserToEvent(String userId, String userName, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado!"));

        long activeRegistrations = event.getRegistrations().stream()
                .filter(EventRegistration::isRegistered)
                .count();

        if (activeRegistrations >= event.getMaxRegistrations()) {
            throw new RuntimeException("Evento está lotado!");
        }

        Optional<EventRegistration> existingRegistration = event.getRegistrations().stream()
                .filter(reg -> reg.getUserId().equals(userId))
                .findFirst();

        if (existingRegistration.isPresent()) {
            EventRegistration registration = existingRegistration.get();

            if (registration.isRegistered()) {
                throw new RuntimeException("Usuário já inscrito neste evento!");
            }

            registration.setRegistered(true);
            return eventRegistrationRepository.save(registration);
        }

        EventRegistration newRegistration = new EventRegistration();
        newRegistration.setUserId(userId);
        newRegistration.setUserName(userName);
        newRegistration.setEvent(event);
        newRegistration.setRegistered(true);

        return eventRegistrationRepository.save(newRegistration);
    }

    public void unregisterUserFromEvent(String userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado!"));

        EventRegistration registration = event.getRegistrations().stream()
                .filter(reg -> reg.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuário não está inscrito neste evento!"));

        event.getRegistrations().remove(registration);
        eventRepository.save(event);

        eventRegistrationRepository.delete(registration);
    }

    public List<EventRegistration> getAllEventRegistrations() {
        return eventRegistrationRepository.findAll();
    }

    public Optional<EventRegistration> getEventRegistrationById(Long id) {
        return eventRegistrationRepository.findById(id);
    }

    public void deleteEventRegistration(Long id) {
        eventRegistrationRepository.deleteById(id);
    }

    public List<Event> getUserRegisteredEvents(String userId) {
        return eventRegistrationRepository.findByUserId(userId)
                .stream()
                .map(EventRegistration::getEvent)
                .collect(Collectors.toList());
    }
}