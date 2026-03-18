package com.project.back_end.controllers;

import java.util.HashMap;
import java.util.Map;

import com.project.back_end.services.Commonservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Patient;
import com.project.back_end.services.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final Commonservice commonservice;

    public PatientController(PatientService patientService, Commonservice commonservice) {
        this.patientService = patientService;
        this.commonservice = commonservice;
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getPatient(@PathVariable String token) {
        if (commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            return patientService.getPatientDetails(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createPatient(@Valid @RequestBody Patient patient) {
        if (commonservice.validatePatient(patient)) {
            int result = patientService.createPatient(patient);
            if (result == 1) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "PatientModel registered successfully");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to register patient");
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("PatientModel already exists with this email or phone");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Login login) {
        return commonservice.validatePatientLogin(login.getEmail(), login.getPassword());
    }

    @GetMapping("/appointments/{id}/{token}")
    public ResponseEntity<?> getPatientAppointment(@PathVariable Long id, @PathVariable String token) {
        if (commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            return patientService.getPatientAppointment(id);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @GetMapping("/appointments/filter/{token}")
    public ResponseEntity<?> filterPatientAppointment(@PathVariable String token, 
                                                      @RequestParam(required = false) String condition, 
                                                      @RequestParam(required = false) String name) {
        if (commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            return commonservice.filterPatient(token, condition, name);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}


