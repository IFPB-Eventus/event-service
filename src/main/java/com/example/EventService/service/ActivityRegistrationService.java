package com.example.EventService.service;

import com.example.EventService.entity.Activity;
import com.example.EventService.entity.ActivityRegistration;
import com.example.EventService.repository.ActivityRegistrationRepository;
import com.example.EventService.repository.ActivityRepository;
import com.example.EventService.repository.EventRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityRegistrationService {

    private final ActivityRegistrationRepository activityRegistrationRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final ActivityRepository activityRepository;

    public ActivityRegistrationService(ActivityRegistrationRepository activityRegistrationRepository,
                                       EventRegistrationRepository eventRegistrationRepository,
                                       ActivityRepository activityRepository) {
        this.activityRegistrationRepository = activityRegistrationRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.activityRepository = activityRepository;
    }

    public ActivityRegistration registerUserToActivity(String userId, Long activityId, String userName) {
        // Verifica se a atividade existe
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada!"));

        Long eventId = activity.getEvent().getId(); // Obtém o ID do evento da atividade

        // Verifica se o usuário está inscrito no evento da atividade
        boolean isUserRegisteredInEvent = eventRegistrationRepository.findByUserIdAndEventId(userId, eventId).isPresent();

        if (!isUserRegisteredInEvent) {
            throw new RuntimeException("Usuário não está inscrito no evento, não pode se inscrever na atividade!");
        }

        // Verifica se o usuário já está inscrito na atividade
        Optional<ActivityRegistration> existingRegistration = activityRegistrationRepository.findByUserIdAndActivityId(userId, activityId);

        if (existingRegistration.isPresent()) {
            throw new RuntimeException("Usuário já está inscrito nesta atividade!");
        }

        // Criar a inscrição na atividade
        ActivityRegistration registration = new ActivityRegistration(userId, activity, userName);
        return activityRegistrationRepository.save(registration);
    }
}