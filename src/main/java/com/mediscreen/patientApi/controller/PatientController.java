package com.mediscreen.patientApi.controller;

import com.mediscreen.patientApi.model.PatientDto;
import com.mediscreen.patientApi.service.PatientService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Api(value = "API to manage patient personnal informations")
public class PatientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public ResponseEntity<List<PatientDto>> getAll(){
        LOGGER.info("GET /getAll");
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientDto> findPatient(@PathVariable("id") int id) {
        LOGGER.info("GET /findPatient patientId = " + id);
        PatientDto patientDto = patientService.getPatientById(id);
        return patientDto != null ? new ResponseEntity<>(patientDto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientDto> updatePatient(@Valid @RequestBody PatientDto patientDto){
        LOGGER.info("PUT /updatePatient patientId = " + patientDto.getId());
        return new ResponseEntity<>(patientService.savePatient(patientDto), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto){
        LOGGER.info("POST /createPatient patient full name = " + patientDto.getFirstName() + " - " + patientDto.getLastName());
        return new ResponseEntity<>(patientService.savePatient(patientDto), HttpStatus.CREATED);
    }
}
