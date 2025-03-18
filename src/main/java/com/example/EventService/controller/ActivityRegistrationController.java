package com.example.EventService.controller;

import com.example.EventService.entity.ActivityRegistration;
import com.example.EventService.service.ActivityRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity-registrations")
public class ActivityRegistrationController {

    private final ActivityRegistrationService activityRegistrationService;

    public ActivityRegistrationController(ActivityRegistrationService activityRegistrationService) {
        this.activityRegistrationService = activityRegistrationService;
    }

    @PostMapping("/{activityId}/register")
    public ResponseEntity<ActivityRegistration> registerUserToActivity(@PathVariable Long activityId, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String userId = jwt.getSubject(); // Obtém o ID do usuário
        String userName = jwt.getClaimAsString("preferred_username");

        ActivityRegistration createdRegistration = activityRegistrationService.registerUserToActivity(userId, activityId, userName);
        return ResponseEntity.ok(createdRegistration);
    }
}