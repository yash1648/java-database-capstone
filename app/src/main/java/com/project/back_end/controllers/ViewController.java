package com.project.back_end.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/adminDashboard";
    }

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard() {
        return "doctor/doctorDashboard";
    }

    @GetMapping("/patient/dashboard")
    public String patientDashboard() {
        return "patient/patientDashboard";
    }

    @GetMapping("/patient/appointments")
    public String patientAppointments() {
        return "patient/patientAppointments";
    }

    @GetMapping("/patient/logged-dashboard")
    public String loggedPatientDashboard() {
        return "patient/loggedPatientDashboard";
    }

    @GetMapping("/patient/record")
    public String patientRecord() {
        return "patient/patientRecord";
    }

    @GetMapping("/prescription/add")
    public String addPrescription() {
        return "prescription/addPrescription";
    }

    @GetMapping("/patient/update-appointment")
    public String updateAppointment() {
        return "patient/updateAppointment";
    }
}
