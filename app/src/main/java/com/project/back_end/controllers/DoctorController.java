package com.project.back_end.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.project.back_end.models.Doctor;
import com.project.back_end.services.DoctorService;

@RestController
@RequestMapping("${api.path}doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public Map<String, Object> getDoctor() {

        List<Doctor> doctors = doctorService.getDoctors();

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);

        return response;
    }

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

}