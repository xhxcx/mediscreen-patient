package com.mediscreen.patientApi.service;

import com.mediscreen.patientApi.model.Patient;
import com.mediscreen.patientApi.model.PatientDto;
import com.mediscreen.patientApi.model.PatientMapper;
import com.mediscreen.patientApi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<PatientDto> getAllPatients() {
        return PatientMapper.INSTANCE.mapListToDto(patientRepository.findAll());
    }

    @Override
    public PatientDto getPatientById(int patientId) {
        //TODO exception to handle ou le orElse fait le job ?
        return PatientMapper.INSTANCE.mapToDto(patientRepository.findById(patientId).orElse(null));
    }

    @Override
    public PatientDto savePatient(@Valid PatientDto patientDto) {
        Patient patientToUpdate = PatientMapper.INSTANCE.mapToEntity(patientDto);
        return PatientMapper.INSTANCE.mapToDto(patientRepository.save(patientToUpdate));
    }
}
