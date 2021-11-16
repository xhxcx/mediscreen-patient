package com.mediscreen.patientApi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    private Timestamp birthDate;

    @NotNull
    @Size(min = 1, max = 1, message = "Gender should be M or F")
    private String gender;

    private String address;

    private String phone;

}
