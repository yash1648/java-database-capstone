package com.project.back_end.services;

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

    @Transactional(readOnly = true)
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public int saveDoctor(Doctor doctor) {

        if (doctorRepository.findByEmail(doctor.getEmail()) != null) {
            return -1;
        }

        doctorRepository.save(doctor);
        return 1;
    }

    @Transactional
    public int updateDoctor(Doctor doctor) {

        Optional<Doctor> existing = doctorRepository.findById(doctor.getId());

        if (existing.isEmpty()) {
            return -1;
        }

        doctorRepository.save(doctor);
        return 1;
    }

    @Transactional
    public int deleteDoctor(Long id) {

        if (!doctorRepository.existsById(id)) {
            return -1;
        }

        doctorRepository.deleteById(id);
        return 1;
    }

}