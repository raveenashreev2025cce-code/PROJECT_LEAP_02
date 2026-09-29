package com.example.OPD_TOKEN_QUEUE.controller;

import com.example.OPD_TOKEN_QUEUE.model.Patient;
import com.example.OPD_TOKEN_QUEUE.service.PatientService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id,
                                 @RequestBody Patient patient) {

        Patient oldPatient = patientService.getPatientById(id);

        oldPatient.setName(patient.getName());
        oldPatient.setAge(patient.getAge());
        oldPatient.setPhone(patient.getPhone());

        return patientService.savePatient(oldPatient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}

