package com.project.back_end.controllers;

import com.project.back_end.services.Commonservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.back_end.models.Appointment;
import com.project.back_end.services.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final Commonservice commonservice;

    public AppointmentController(AppointmentService appointmentService, Commonservice commonservice) {
        this.appointmentService = appointmentService;
        this.commonservice = commonservice;
    }

    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<?> getAppointments(@PathVariable String date, @PathVariable String patientName, @PathVariable String token) {
        if (commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            // This is a bit simplified, ideally we'd filter by doctor from token
            return ResponseEntity.ok(appointmentService.getAppointments());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/{token}")
    public ResponseEntity<?> bookAppointment(@Valid @RequestBody Appointment appointment, @PathVariable String token) {
        if (commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            int result = appointmentService.bookAppointment(appointment);
            if (result == 1) {
                return ResponseEntity.ok("Appointment booked successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to book appointment");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PutMapping("/{token}")
    public ResponseEntity<?> updateAppointment(@Valid @RequestBody Appointment appointment, @PathVariable String token) {
        if (commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            // Using save for update since AppointmentService doesn't have update specifically
            int result = appointmentService.bookAppointment(appointment);
            if (result == 1) {
                return ResponseEntity.ok("Appointment updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update appointment");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id, @PathVariable String token) {
        if (commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            int result = appointmentService.cancelAppointment(id);
            if (result == 1) {
                return ResponseEntity.ok("Appointment cancelled successfully");
            } else if (result == -1) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to cancel appointment");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
