package com.project.back_end.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.back_end.models.Appointment;
import com.project.back_end.repo.AppointmentRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public int bookAppointment(Appointment appointment) {

        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Transactional
    public int cancelAppointment(Long id) {

        if (!appointmentRepository.existsById(id)) {
            return -1;
        }

        appointmentRepository.deleteById(id);
        return 1;
    }
}