package com.project.back_end.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;

class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDoctors() {
        Doctor doc1 = new Doctor();
        doc1.setId(1L);
        Doctor doc2 = new Doctor();
        doc2.setId(2L);
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doc1, doc2));

        List<Doctor> result = doctorService.getAllDoctors();

        assertEquals(2, result.size());
        verify(doctorRepository).findAll();
    }

    @Test
    void testGetDoctorById() {
        Doctor doc = new Doctor();
        doc.setId(1L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doc));

        Optional<Doctor> result = doctorService.getDoctorById(1L);

        assertEquals(true, result.isPresent());
        assertEquals(1L, result.get().getId());
    }
}
