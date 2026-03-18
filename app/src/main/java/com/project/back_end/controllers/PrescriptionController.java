package com.project.back_end.controllers;

import org.springframework.web.bind.annotation.*;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.PrescriptionService;

@RestController
@RequestMapping("${api.path}prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/{token}")
    public Prescription savePrescription(
            @RequestBody Prescription prescription,
            @PathVariable String token) {

        return prescriptionService.savePrescription(prescription);
    }

}