package com.example.OPD_TOKEN_QUEUE.repository;

import com.example.OPD_TOKEN_QUEUE.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {

}
