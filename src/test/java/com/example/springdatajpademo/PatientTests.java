package com.example.springdatajpademo;

import com.example.springdatajpademo.dto.BloodGrpResponseEntity;
import com.example.springdatajpademo.entities.Patient;
import com.example.springdatajpademo.patientservices.PatientService;
import com.example.springdatajpademo.repo.PatientRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
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

    @Test
    public void testFindByNameMethod(){
        Patient patient1 = patientService.getPatientByName("Aarav");
        System.out.println(patient1);
    }
    @Test
    public void testFindByBloodGrp(){
        List<Patient> patient = patientRepo.findByBloodGrp("A positive");
        System.out.println(patient);
    }
    @Test
    public void testFindByBirthDateBetween(){
        List<Patient> patientList = patientRepo.findByBirthDateBetween(LocalDate.of(2006,7,17),LocalDate.of(2010,12,12));
        for(Patient patient:patientList){
            System.out.println(patient);
        }
    }
    @Test
    public void testFindBornAfterDateMethod(){
        List<Patient> patientList = patientRepo.findBornAfterDate(LocalDate.of(2006,8,10));
        for (Patient patient:patientList){
            System.out.println(patient);
        }
    }
    //Projection In JPQL
    @Test
    public void testCountEachBloodGrpTypeMethod(){
        List<BloodGrpResponseEntity> bloodGrpList = patientRepo.countEachBloodGrpType();
        for (BloodGrpResponseEntity bloodGrpResponse :bloodGrpList){
            System.out.println(bloodGrpResponse);
        }
    }
    //Pagination
    @Test
    public void testFindByAllMethod(){
        //List<Patient> patientList = patientRepo.findByAll();
        Page<Patient> patientList = patientRepo.findByAll(PageRequest.of(0,3));
        for (Patient patient:patientList){
            System.out.println(patient);
        }
    }

}
