package com.mediscreen.patientApi.controller;

import com.mediscreen.patientApi.model.PatientDto;
import com.mediscreen.patientApi.service.PatientService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PatientControllerTest {

    @MockBean
    private PatientService patientServiceMock;

    @Autowired
    private MockMvc mockMvc;

    private final PatientDto patientDto = new PatientDto();

    @BeforeEach
    public void setUpTests(){
        patientDto.setId(1);
        patientDto.setFirstName("firstName");
        patientDto.setLastName("lastName");
        patientDto.setBirthDate(LocalDate.of(2021, 11, 15));
        patientDto.setGender("X");
        patientDto.setAddress("address");
        patientDto.setPhone("phone");
    }

    @Test
    public void getAllShouldReturn200AndAListOfPatientDTO() throws Exception {
        Mockito.when(patientServiceMock.getAllPatients()).thenReturn(Collections.singletonList(patientDto));
        String expectedContent = "{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"birthDate\":\"2021-11-15\",\"gender\":\"X\",\"address\":\"address\",\"phone\":\"phone\"}";

        mockMvc.perform(get("/"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(expectedContent));
                });
    }

    @Test
    public void findPatientShouldReturn200AndAPatientDTO() throws Exception {
        Mockito.when(patientServiceMock.getPatientById(1)).thenReturn(patientDto);
        String expectedContent = "{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"birthDate\":\"2021-11-15\",\"gender\":\"X\",\"address\":\"address\",\"phone\":\"phone\"}";

        mockMvc.perform(get("/" + patientDto.getId()))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(expectedContent));
                });
    }

    @Test
    public void findPatientShouldReturn404() throws Exception {
        Mockito.when(patientServiceMock.getPatientById(1)).thenReturn(null);

        mockMvc.perform(get("/" + 1))
                .andExpect(mvcResult ->
                    Assert.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus()));
    }

    @Test
    public void updatePatientShouldReturn200AndUpdatedPatientDto() throws Exception {
        PatientDto updatedPatient = new PatientDto();
        updatedPatient.setId(1);
        updatedPatient.setFirstName("firstName");
        updatedPatient.setLastName("lastName");
        updatedPatient.setBirthDate(LocalDate.of(2021, 11, 15));
        updatedPatient.setGender("X");
        updatedPatient.setAddress("new address");
        updatedPatient.setPhone("phone");
        Mockito.when(patientServiceMock.savePatient(any(PatientDto.class))).thenReturn(updatedPatient);

        String paramPatient = "{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"birthDate\":\"2021-11-15\",\"gender\":\"X\",\"address\":\"new address\",\"phone\":\"phone\"}";
        mockMvc.perform(put("/" + patientDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(paramPatient))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(paramPatient));
                });

    }

    @Test
    public void createPatientShouldReturn201AndCreatedPatientDto() throws Exception {
        PatientDto newPatient = new PatientDto();
        newPatient.setId(100);
        newPatient.setFirstName("new");
        newPatient.setLastName("Patient");
        newPatient.setBirthDate(LocalDate.of(2021, 11, 16));
        newPatient.setGender("Z");
        newPatient.setAddress("address");
        newPatient.setPhone("phone");
        Mockito.when(patientServiceMock.savePatient(any(PatientDto.class))).thenReturn(newPatient);

        String paramPatient = "{\"id\":100,\"firstName\":\"new\",\"lastName\":\"Patient\",\"birthDate\":\"2021-11-16\",\"gender\":\"Z\",\"address\":\"address\",\"phone\":\"phone\"}";
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(paramPatient))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
                    Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(paramPatient));
                });

    }
}
