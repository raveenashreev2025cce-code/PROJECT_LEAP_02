package com.example.OPD_TOKEN_QUEUE.repository;

import com.example.OPD_TOKEN_QUEUE.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
