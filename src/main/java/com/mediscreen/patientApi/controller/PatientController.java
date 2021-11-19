package com.mediscreen.patientApi.controller;

import com.mediscreen.patientApi.model.PatientDto;
import com.mediscreen.patientApi.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("API to manage patient personnal informations")
public class PatientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @ApiOperation(value = "get all patient as a list of patient")
    @GetMapping("/")
    public ResponseEntity<List<PatientDto>> getAll(){
        LOGGER.info("GET /getAll");
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @ApiOperation(value = "find a patient with its id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientDto> findPatient(@PathVariable("id") int id) {
        LOGGER.info("GET /findPatient patientId = " + id);
        PatientDto patientDto = patientService.getPatientById(id);
        return patientDto != null ? new ResponseEntity<>(patientDto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "update a patient")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("id") int id, @Valid @RequestBody PatientDto patientDto){
        LOGGER.info("PUT /updatePatient patientId = " + id);
        return new ResponseEntity<>(patientService.savePatient(patientDto), HttpStatus.OK);
    }

    @ApiOperation(value = "create a new patient")
    @PostMapping(value = "/")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto){
        LOGGER.info("POST /createPatient patient full name = " + patientDto.getFirstName() + " - " + patientDto.getLastName());
        return new ResponseEntity<>(patientService.savePatient(patientDto), HttpStatus.CREATED);
    }
}
