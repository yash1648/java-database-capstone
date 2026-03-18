package com.project.back_end.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.back_end.models.Doctor;
import com.project.back_end.services.DoctorService;

@WebMvcTest(DoctorRestController.class)
class DoctorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Test
    void testGetAllDoctors() throws Exception {
        Doctor doc1 = new Doctor();
        doc1.setId(1L);
        doc1.setFirstName("John");
        doc1.setLastName("Doe");

        when(doctorService.getAllDoctors()).thenReturn(Arrays.asList(doc1));

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }
}
