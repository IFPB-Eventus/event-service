package com.example.EventService.service;

import com.example.EventService.entity.Attendance;
import com.example.EventService.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    private final AttendanceRepository attendanceRepository;

    public CertificateService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<String> generateCertificatesForActivity(Long activityId) {
        List<Attendance> attendanceList = attendanceRepository.findByActivityId(activityId);

        return attendanceList.stream()
                .filter(Attendance::isPresent) // Filtra os presentes
                .map(Attendance::getUserId)    // Retorna apenas os IDs dos usuários
                .collect(Collectors.toList());
    }
}
