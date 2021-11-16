package com.mediscreen.patientApi.service;

import com.mediscreen.patientApi.model.PatientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    List<PatientDto> getAllPatients();
    PatientDto getPatientById(int patientId);
    PatientDto savePatient(PatientDto patientDto);
}
