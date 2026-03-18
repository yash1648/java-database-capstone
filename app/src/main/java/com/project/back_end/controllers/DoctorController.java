package com.project.back_end.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.project.back_end.models.Doctor;
import com.project.back_end.services.DoctorService;
import com.project.back_end.services.TokenService;

@RestController
@RequestMapping("${api.path}doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenService tokenService;

    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService;
    }

    /**
     * Get all doctors
     */
    @GetMapping
    public Map<String, Object> getDoctor() {

        List<Doctor> doctors = doctorService.getDoctors();

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);

        return response;
    }

    /**
     * Save new doctor
     */
    @PostMapping
    public Map<String, String> saveDoctor(@RequestBody Doctor doctor) {

        int result = doctorService.saveDoctor(doctor);

        Map<String, String> response = new HashMap<>();

        if (result == 1) {
            response.put("message", "Doctor added successfully");
        } else {
            response.put("message", "Doctor already exists");
        }

        return response;
    }

    /**
     * Get doctor availability
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public Map<String, Object> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable String date,
            @PathVariable String token) {

        Map<String, Object> response = new HashMap<>();

        // Validate token
        boolean valid = tokenService.validateToken(token, user);

        if (!valid) {
            response.put("error", "Invalid token");
            return response;
        }

        // Convert date
        LocalDate appointmentDate = LocalDate.parse(date);

        // Get availability
        List<String> availability =
                doctorService.getDoctorAvailability(doctorId, appointmentDate);

        response.put("availability", availability);

        return response;
    }
}