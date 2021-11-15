package com.mediscreen.patientApi.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient mapToEntity(PatientDto patientDto);
    PatientDto mapToDto(Patient patient);
    List<PatientDto> mapListToDto(List<Patient> patientList);
}
