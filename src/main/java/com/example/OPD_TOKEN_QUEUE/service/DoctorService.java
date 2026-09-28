package com.example.OPD_TOKEN_QUEUE.service;
import com.example.OPD_TOKEN_QUEUE.model.Doctor;

import java.util.List;
public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Long id);
    void deleteDoctor(Long id);
}
