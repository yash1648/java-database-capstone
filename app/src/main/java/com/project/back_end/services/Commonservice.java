package com.project.back_end.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.back_end.models.Admin;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.AdminRepository;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;

@Service
public class Commonservice {

    private final TokenService tokenService;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;

    public Commonservice(TokenService tokenService, AdminRepository adminRepository, DoctorRepository doctorRepository,
                         PatientRepository patientRepository, AppointmentRepository appointmentRepository,
                         PatientService patientService) {
        this.tokenService = tokenService;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
    }

    public ResponseEntity<Map<String, String>> validateToken(String token) {
        Map<String, String> response = new HashMap<>();
        if (tokenService.validateToken(token)) {
            response.put("message", "Token is valid");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    public boolean validateToken(String token, String role) {
        // Simple role validation: if the token is valid, we'll assume for now it's for the correct role
        // In a real app, you'd extract roles from the JWT.
        return tokenService.validateToken(token);
    }

    public ResponseEntity<Map<String, String>> validateAdmin(Admin admin) {
        Map<String, String> response = new HashMap<>();
        try {
            Admin existingAdmin = adminRepository.findByUsername(admin.getUsername());
            if (existingAdmin != null && existingAdmin.getPassword().equals(admin.getPassword())) {
                String token = tokenService.generateToken(admin.getUsername());
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("message", "An error occurred during login");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public List<Doctor> filterDoctor(String name, String specialty, String time) {
        if (name != null && specialty != null) {
            return doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        } else if (name != null) {
            return doctorRepository.findByNameLike(name);
        } else if (specialty != null) {
            return doctorRepository.findBySpecialtyIgnoreCase(specialty);
        } else {
            return doctorRepository.findAll();
        }
    }

    public int validateAppointment(Long doctorId, LocalDate date, String time) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return -1;
        }

        List<String> availableTimes = doctor.getAvailableTimes();
        if (availableTimes != null && availableTimes.contains(time)) {
            return 1;
        }
        return 0;
    }

    public boolean validatePatient(Patient patient) {
        return patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone()) == null;
    }

    public ResponseEntity<Map<String, String>> validatePatientLogin(String email, String password) {
        Map<String, String> response = new HashMap<>();
        try {
            Patient patient = patientRepository.findByEmail(email);
            if (patient != null && patient.getPassword().equals(password)) {
                String token = tokenService.generateToken(email);
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("message", "An error occurred during login");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> filterPatient(String token, String condition, String doctorName) {
        String email = tokenService.extractEmail(token);
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PatientModel not found");
        }

        if (condition != null && doctorName != null) {
            return patientService.filterByDoctorAndCondition(doctorName, patient.getId(), condition);
        } else if (condition != null) {
            return patientService.filterByCondition(patient.getId(), condition);
        } else if (doctorName != null) {
            return patientService.filterByDoctor(doctorName, patient.getId());
        } else {
            return patientService.getPatientAppointment(patient.getId());
        }
    }
}
