package com.project.back_end.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.back_end.models.Doctor;
import com.project.back_end.repo.DoctorRepository;

class DoctorCommonserviceTest {

    @Mock
    private DoctorRepository doctorRepository;

    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void testGetDoctors() {
        Doctor doc1 = new Doctor();
        Doctor doc2 = new Doctor();
        List<Doctor> doctors = Arrays.asList(doc1, doc2);
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getDoctors();

        assertEquals(2, result.size());
        verify(doctorRepository).findAll();
    }

    @Test
    void testSaveDoctorSuccess() {
        Doctor doctor = new Doctor();
        doctor.setEmail("test@example.com");
        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(null);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        int result = doctorService.saveDoctor(doctor);

        assertEquals(1, result);
        verify(doctorRepository).save(doctor);
    }

    @Test
    void testSaveDoctorAlreadyExists() {
        Doctor doctor = new Doctor();
        doctor.setEmail("test@example.com");
        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(new Doctor());

        int result = doctorService.saveDoctor(doctor);

        assertEquals(-1, result);
    }

    @Test
    void testUpdateDoctorSuccess() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(new Doctor()));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        int result = doctorService.updateDoctor(doctor);

        assertEquals(1, result);
        verify(doctorRepository).save(doctor);
    }

    @Test
    void testDeleteDoctorSuccess() {
        Long id = 1L;
        when(doctorRepository.existsById(id)).thenReturn(true);

        int result = doctorService.deleteDoctor(id);

        assertEquals(1, result);
        verify(doctorRepository).deleteById(id);
    }
}
