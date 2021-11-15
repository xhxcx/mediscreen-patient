package com.mediscreen.patientApi.service;

import com.mediscreen.patientApi.model.Patient;
import com.mediscreen.patientApi.model.PatientMapper;
import com.mediscreen.patientApi.repository.PatientRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepositoryMock;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    public void getAllPatientsTest() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("firstName");
        patient.setLastName("lastName");
        patient.setBirthDate(Timestamp.valueOf(LocalDateTime.of(2021, Month.NOVEMBER, 15, 10,30)));
        patient.setGender("X");
        patient.setAddress("address");
        patient.setPhone("phone");

        Mockito.when(patientRepositoryMock.findAll()).thenReturn(Collections.singletonList(patient));

        Assert.assertTrue(patientService.getAllPatients().containsAll(PatientMapper.INSTANCE.mapListToDto(Collections.singletonList(patient))));
    }

    @Test
    public void getPatientByIdWhenPatientExistsTest() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("firstName");
        patient.setLastName("lastName");
        patient.setBirthDate(Timestamp.valueOf(LocalDateTime.of(2021, Month.NOVEMBER, 15, 10,30)));
        patient.setGender("X");
        patient.setAddress("address");
        patient.setPhone("phone");

        Mockito.when(patientRepositoryMock.findById(1)).thenReturn(java.util.Optional.of(patient));

        Assert.assertTrue(patientService.getPatientById(1).toString().equalsIgnoreCase(PatientMapper.INSTANCE.mapToDto(patient).toString()));
    }

    @Test
    public void getPatientByIdWhenPatientDoesNotExistsTest() {
        Mockito.when(patientRepositoryMock.findById(0)).thenReturn(Optional.empty());

        Assert.assertNull(patientService.getPatientById(0));
    }
}
