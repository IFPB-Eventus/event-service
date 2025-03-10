package com.example.EventService.repository;

import com.example.EventService.entity.EventPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPlanRepository extends JpaRepository<EventPlan, Long> {
}