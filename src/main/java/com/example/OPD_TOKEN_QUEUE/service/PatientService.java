package com.example.OPD_TOKEN_QUEUE.service;

import com.example.OPD_TOKEN_QUEUE.model.Patient;

import java.util.*;

public interface PatientService {
    Patient savePatient(Patient patient);
    List<Patient> getAllPatients();
    Patient getPatientById(Long Id);
    void deletePatient(Long Id);
}
