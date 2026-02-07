package com.example.springdatajpademo;

import com.example.springdatajpademo.entities.Patient;
import com.example.springdatajpademo.patientservices.PatientService;
import com.example.springdatajpademo.repo.PatientRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientTests {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientService patientService;
    @Test
    public void testPatientRepository(){
        List<Patient> patientList = patientRepo.findAll();
        System.out.println(patientList);
    }

    @Test
    public void testTransactionMethods(){
        Patient patient = patientService.getPatientById(1L);
        System.out.println(patient);
    }
}
