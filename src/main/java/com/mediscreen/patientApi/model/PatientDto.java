package com.mediscreen.patientApi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
public class PatientDto {

    private int id;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp birthDate;

    @NotNull
    @Size(min = 1, max = 1, message = "Gender should be M or F")
    private String gender;

    private String address;

    private String phone;
}
