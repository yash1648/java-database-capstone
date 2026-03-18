package com.project.back_end.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.project.back_end.models.Prescription;
import com.project.back_end.repo.PrescriptionRepository;

class PrescriptionCommonserviceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    private PrescriptionService prescriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        prescriptionService = new PrescriptionService(prescriptionRepository);
    }

    @Test
    void testSavePrescriptionSuccess() {
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(1L);
        when(prescriptionRepository.findByAppointmentId(1L)).thenReturn(Collections.emptyList());
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);

        ResponseEntity<?> response = prescriptionService.savePrescription(prescription);

        assertEquals(201, response.getStatusCodeValue());
        verify(prescriptionRepository).save(prescription);
    }

    @Test
    void testSavePrescriptionAlreadyExists() {
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(1L);
        when(prescriptionRepository.findByAppointmentId(1L)).thenReturn(Collections.singletonList(new Prescription()));

        ResponseEntity<?> response = prescriptionService.savePrescription(prescription);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetPrescriptionSuccess() {
        Long appointmentId = 1L;
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(appointmentId);
        when(prescriptionRepository.findByAppointmentId(appointmentId)).thenReturn(Collections.singletonList(prescription));

        ResponseEntity<?> response = prescriptionService.getPrescription(appointmentId);

        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals(prescription, body.get("prescription"));
    }
}
