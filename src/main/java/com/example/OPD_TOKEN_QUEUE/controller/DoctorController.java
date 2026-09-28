package com.example.OPD_TOKEN_QUEUE.controller;

import com.example.OPD_TOKEN_QUEUE.model.Doctor;
import com.example.OPD_TOKEN_QUEUE.service.DoctorService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // CREATE
    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    // READ ALL
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // READ ONE
    @GetMapping("/{id}")
    public Doctor getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id,
                               @RequestBody Doctor doctor) {

        Doctor oldDoctor = doctorService.getDoctorById(id);

        oldDoctor.setName(doctor.getName());
        oldDoctor.setDepartment(doctor.getDepartment());

        return doctorService.saveDoctor(oldDoctor);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
