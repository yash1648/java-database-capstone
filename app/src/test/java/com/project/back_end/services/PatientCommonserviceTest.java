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
import org.springframework.http.ResponseEntity;

import com.project.back_end.DTO.AppointmentDTO;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.PatientRepository;

class PatientCommonserviceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private TokenService tokenService;

    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository, appointmentRepository, tokenService);
    }

    @Test
    void testCreatePatientSuccess() {
        Patient patient = new Patient();
        when(patientRepository.save(patient)).thenReturn(patient);

        int result = patientService.createPatient(patient);

        assertEquals(1, result);
        verify(patientRepository).save(patient);
    }

    @Test
    void testGetPatientAppointment() {
        Long patientId = 1L;
        Appointment app1 = new Appointment();
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Smith");
        app1.setDoctor(doctor);
        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setName("John Doe");
        app1.setPatient(patient);
        List<Appointment> appointments = Arrays.asList(app1);
        when(appointmentRepository.findByPatientId(patientId)).thenReturn(appointments);

        ResponseEntity<List<AppointmentDTO>> response = patientService.getPatientAppointment(patientId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Dr. Smith", response.getBody().get(0).getDoctorName());
    }
}
