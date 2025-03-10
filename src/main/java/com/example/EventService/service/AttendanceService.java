package com.example.EventService.service;

import com.example.EventService.entity.Activity;
import com.example.EventService.entity.Attendance;
import com.example.EventService.repository.ActivityRepository;
import com.example.EventService.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final ActivityRepository activityRepository;
    private final KeycloakUserService keycloakUserService; // Novo serviço

    public AttendanceService(AttendanceRepository attendanceRepository, ActivityRepository activityRepository, KeycloakUserService keycloakUserService) {
        this.attendanceRepository = attendanceRepository;
        this.activityRepository = activityRepository;
        this.keycloakUserService = keycloakUserService;
    }

    public Attendance markAttendance(Long activityId, String userId, boolean present) {
        // Verifica se o usuário existe no Keycloak
        if (!keycloakUserService.userExists(userId)) {
            throw new RuntimeException("Usuário não encontrado no Keycloak!");
        }

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada!"));

        Attendance attendance = new Attendance();
        attendance.setActivity(activity);
        attendance.setUserId(userId);
        attendance.setPresent(present);

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceForActivity(Long activityId) {
        return attendanceRepository.findByActivityId(activityId);
    }
}
