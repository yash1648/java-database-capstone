package com.project.back_end.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.back_end.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Retrieves a patient using their email address.
     *
     * @param email the email of the patient
     * @return the patient object if found, otherwise null
     */
    Patient findByEmail(String email);

    /**
     * Retrieves a patient using either email or phone number.
     *
     * @param email the email of the patient
     * @param phone the phone number of the patient
     * @return the patient object if a match is found by email or phone
     */
    Patient findByEmailOrPhone(String email, String phone);

}