package com.example.EventService.repository;

import com.example.EventService.entity.ActivityRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRegistrationRepository extends JpaRepository<ActivityRegistration, Long> {
    Optional<ActivityRegistration> findByUserIdAndActivityId(String userId, Long activityId);
}