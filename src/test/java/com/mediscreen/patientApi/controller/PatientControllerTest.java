package com.mediscreen.patientApi.controller;

import com.mediscreen.patientApi.model.PatientDto;
import com.mediscreen.patientApi.service.PatientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PatientControllerTest {

    @MockBean
    private PatientService patientServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllShouldReturn200AndAListOfPatientDTO() throws Exception {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(1);
        patientDto.setFirstName("firstName");
        patientDto.setLastName("lastName");
        patientDto.setBirthDate(Timestamp.valueOf(LocalDateTime.of(2021, Month.NOVEMBER, 15, 10,30)));
        patientDto.setGender("X");
        patientDto.setAddress("address");
        patientDto.setPhone("phone");

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
        PatientDto patientDto = new PatientDto();
        patientDto.setId(1);
        patientDto.setFirstName("firstName");
        patientDto.setLastName("lastName");
        patientDto.setBirthDate(Timestamp.valueOf(LocalDateTime.of(2021, Month.NOVEMBER, 15, 10,30)));
        patientDto.setGender("X");
        patientDto.setAddress("address");
        patientDto.setPhone("phone");

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
        PatientDto patientDto = new PatientDto();
        patientDto.setId(1);
        patientDto.setFirstName("firstName");
        patientDto.setLastName("lastName");
        patientDto.setBirthDate(Timestamp.valueOf(LocalDateTime.of(2021, Month.NOVEMBER, 15, 10,30)));
        patientDto.setGender("X");
        patientDto.setAddress("new address");
        patientDto.setPhone("phone");

        Mockito.when(patientServiceMock.updatePatient(any(PatientDto.class))).thenReturn(patientDto);

        String paramPatient = "{\"id\":1,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"birthDate\":\"2021-11-15\",\"gender\":\"X\",\"address\":\"new address\",\"phone\":\"phone\"}";
        mockMvc.perform(put("/" + patientDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(paramPatient))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(paramPatient));
                });

    }
}
