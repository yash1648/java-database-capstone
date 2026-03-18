package com.project.back_end.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.back_end.models.Doctor;
import com.project.back_end.repo.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Get all doctors
     */
    @Transactional(readOnly = true)
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Save new doctor
     */
    @Transactional
    public int saveDoctor(Doctor doctor) {

        if (doctorRepository.findByEmail(doctor.getEmail()) != null) {
            return -1;
        }

        doctorRepository.save(doctor);
        return 1;
    }

    /**
     * Update doctor
     */
    @Transactional
    public int updateDoctor(Doctor doctor) {

        Optional<Doctor> existing = doctorRepository.findById(doctor.getId());

        if (existing.isEmpty()) {
            return -1;
        }

        doctorRepository.save(doctor);
        return 1;
    }

    /**
     * Delete doctor
     */
    @Transactional
    public int deleteDoctor(Long id) {

        if (!doctorRepository.existsById(id)) {
            return -1;
        }

        doctorRepository.deleteById(id);
        return 1;
    }

    /**
     * Validate doctor login credentials
     */
    @Transactional(readOnly = true)
    public Doctor validateDoctor(String email, String password) {

        Doctor doctor = doctorRepository.findByEmail(email);

        if (doctor != null && doctor.getPassword().equals(password)) {
            return doctor;
        }

        return null;
    }

    /**
     * Get available time slots for a doctor on a given date
     * (basic implementation returning stored availableTimes)
     */
    @Transactional(readOnly = true)
    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (doctor.isEmpty()) {
            return null;
        }

        return doctor.get().getAvailableTimes();
    }
}