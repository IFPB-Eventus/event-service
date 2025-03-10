package com.example.EventService.controller;

import com.example.EventService.entity.Attendance;
import com.example.EventService.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // Registrar presença
    @PostMapping("/{activityId}/{userId}")
    public ResponseEntity<Attendance> markAttendance(@PathVariable Long activityId, @PathVariable String userId, @RequestParam boolean present) {
        Attendance attendance = attendanceService.markAttendance(activityId, userId, present);
        return ResponseEntity.ok(attendance);
    }

    // Buscar lista de presença de uma atividade
    @GetMapping("/{activityId}")
    public ResponseEntity<List<Attendance>> getAttendanceList(@PathVariable Long activityId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceForActivity(activityId);
        return ResponseEntity.ok(attendanceList);
    }
}
