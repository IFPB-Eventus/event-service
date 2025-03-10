package com.example.EventService.service;

import com.example.EventService.entity.Event;
import com.example.EventService.entity.EventRegistration;
import com.example.EventService.repository.EventRegistrationRepository;
import com.example.EventService.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository, EventRepository eventRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventRepository = eventRepository;
    }

    public EventRegistration registerUserToEvent(String userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado!"));

        // Verificar se o evento atingiu o limite de inscrições
        if (event.getRegistrations().size() >= event.getMaxRegistrations()) {
            throw new RuntimeException("Evento está lotado!");
        }

        // Verificar se o usuário já está inscrito
        boolean alreadyRegistered = event.getRegistrations().stream()
                .anyMatch(reg -> reg.getUserId().equals(userId));

        if (alreadyRegistered) {
            throw new RuntimeException("Usuário já inscrito neste evento!");
        }

        // Criar inscrição
        EventRegistration registration = new EventRegistration();
        registration.setUserId(userId);
        registration.setEvent(event);

        return eventRegistrationRepository.save(registration);
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
}