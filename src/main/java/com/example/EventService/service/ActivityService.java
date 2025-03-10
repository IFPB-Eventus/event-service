package com.example.EventService.service;

import com.example.EventService.entity.Activity;
import com.example.EventService.entity.Event;
import com.example.EventService.repository.ActivityRepository;
import com.example.EventService.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final EventRepository eventRepository;

    public ActivityService(ActivityRepository activityRepository, EventRepository eventRepository) {
        this.activityRepository = activityRepository;
        this.eventRepository = eventRepository;
    }

    public Activity createActivity(Activity activity, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado!"));

        activity.setEvent(event);
        return activityRepository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
}