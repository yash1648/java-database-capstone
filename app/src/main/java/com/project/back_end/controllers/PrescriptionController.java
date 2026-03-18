package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.Commonservice;
import com.project.back_end.services.PrescriptionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.path}prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final Commonservice commonservice;

    public PrescriptionController(PrescriptionService prescriptionService, Commonservice commonservice) {
        this.prescriptionService = prescriptionService;
        this.commonservice = commonservice;
    }

    /**
     * Save prescription
     */
    @PostMapping("/{token}")
    public ResponseEntity<?> savePrescription(
            @Valid @RequestBody Prescription prescription,
            @PathVariable String token) {

        // Validate token
        if (!commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        try {
            prescriptionService.savePrescription(prescription);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Prescription saved successfully");

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving prescription");
        }
    }

    /**
     * Get prescription by appointment ID
     */
    @GetMapping("/{appointmentId}/{token}")
    public ResponseEntity<?> getPrescription(
            @PathVariable Long appointmentId,
            @PathVariable String token) {

        // Validate token
        if (!commonservice.validateToken(token).getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        Prescription prescription = prescriptionService.getPrescription(appointmentId);

        if (prescription == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prescription not found");
        }

        return ResponseEntity.ok(prescription);
    }
}