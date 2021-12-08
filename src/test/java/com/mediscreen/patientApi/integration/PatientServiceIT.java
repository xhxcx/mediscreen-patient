package com.mediscreen.patientApi.integration;

import com.mediscreen.patientApi.model.Patient;
import com.mediscreen.patientApi.model.PatientMapper;
import com.mediscreen.patientApi.repository.PatientRepository;
import com.mediscreen.patientApi.service.PatientServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@Sql({"/sql/DDL.sql", "/sql/data.sql"})
public class PatientServiceIT {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void savePatientIT(){
        Patient newPatient = new Patient();
        newPatient.setFirstName("Integration");
        newPatient.setLastName("Test");
        newPatient.setBirthDate(LocalDate.of(2021,11,25));
        newPatient.setGender("F");

        patientService.savePatient(PatientMapper.INSTANCE.mapToDto(newPatient));
        List<Patient> patientList = patientRepository.findAll();

        Assert.assertEquals(2, patientList.size());
        Assert.assertTrue(patientList.stream().anyMatch(patient -> patient.getFirstName().equalsIgnoreCase(newPatient.getFirstName())));
    }
}
