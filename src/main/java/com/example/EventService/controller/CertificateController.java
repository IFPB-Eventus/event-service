package com.example.EventService.controller;

import com.example.EventService.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<List<String>> generateCertificates(@PathVariable Long activityId) {
        List<String> certifiedUsers = certificateService.generateCertificatesForActivity(activityId);
        return ResponseEntity.ok(certifiedUsers);
    }
}
