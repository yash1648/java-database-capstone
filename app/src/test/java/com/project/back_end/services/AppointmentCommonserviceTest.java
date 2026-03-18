package com.project.back_end.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.back_end.models.Appointment;
import com.project.back_end.repo.AppointmentRepository;

class AppointmentCommonserviceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentService(appointmentRepository);
    }

    @Test
    void testBookAppointmentSuccess() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        int result = appointmentService.bookAppointment(appointment);

        assertEquals(1, result);
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void testGetAppointments() {
        Appointment app1 = new Appointment();
        Appointment app2 = new Appointment();
        List<Appointment> appointments = Arrays.asList(app1, app2);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointments();

        assertEquals(2, result.size());
        verify(appointmentRepository).findAll();
    }

    @Test
    void testCancelAppointmentSuccess() {
        Long id = 1L;
        when(appointmentRepository.existsById(id)).thenReturn(true);

        int result = appointmentService.cancelAppointment(id);

        assertEquals(1, result);
        verify(appointmentRepository).deleteById(id);
    }

    @Test
    void testCancelAppointmentNotFound() {
        Long id = 1L;
        when(appointmentRepository.existsById(id)).thenReturn(false);

        int result = appointmentService.cancelAppointment(id);

        assertEquals(-1, result);
    }
}
