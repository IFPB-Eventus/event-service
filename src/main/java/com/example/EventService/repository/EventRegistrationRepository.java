package com.example.EventService.repository;

import com.example.EventService.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    Optional<EventRegistration> findByUserIdAndEventId(String userId, Long eventId);
}