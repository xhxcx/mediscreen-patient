package com.mediscreen.patientApi.service;

import com.mediscreen.patientApi.model.Patient;
import com.mediscreen.patientApi.model.PatientDto;
import com.mediscreen.patientApi.model.PatientMapper;
import com.mediscreen.patientApi.repository.PatientRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepositoryMock;

    @InjectMocks
    private PatientServiceImpl patientService;

    private final Patient patient = new Patient();;

    @BeforeEach
    public void setUpTests(){
        patient.setId(1);
        patient.setFirstName("firstName");
        patient.setLastName("lastName");
        patient.setBirthDate(LocalDate.of(2021, 11, 15));
        patient.setGender("X");
        patient.setAddress("address");
        patient.setPhone("phone");
    }

    @Test
    public void getAllPatientsTest() {
        Mockito.when(patientRepositoryMock.findAll()).thenReturn(Collections.singletonList(patient));

        Assert.assertTrue(patientService.getAllPatients().containsAll(PatientMapper.INSTANCE.mapListToDto(Collections.singletonList(patient))));
    }

    @Test
    public void getPatientByIdWhenPatientExistsTest() {
        Mockito.when(patientRepositoryMock.findById(1)).thenReturn(java.util.Optional.of(patient));

        Assert.assertTrue(patientService.getPatientById(1).toString().equalsIgnoreCase(PatientMapper.INSTANCE.mapToDto(patient).toString()));
    }

    @Test
    public void getPatientByIdWhenPatientDoesNotExistsTest() {
        Mockito.when(patientRepositoryMock.findById(0)).thenReturn(Optional.empty());

        Assert.assertNull(patientService.getPatientById(0));
    }

    @Test
    public void savePatientTest(){
        Patient updatePatient = new Patient();
        updatePatient.setId(1);
        updatePatient.setFirstName("firstName");
        updatePatient.setLastName("lastName");
        updatePatient.setBirthDate(LocalDate.of(2021, 11, 15));
        updatePatient.setGender("X");
        updatePatient.setAddress("address");
        updatePatient.setPhone("updated phone");
        Mockito.when(patientRepositoryMock.save(updatePatient)).thenReturn(updatePatient);

        PatientDto resultPatientDto = patientService.savePatient(PatientMapper.INSTANCE.mapToDto(updatePatient));

        Assert.assertEquals("updated phone", resultPatientDto.getPhone());
    }
}
