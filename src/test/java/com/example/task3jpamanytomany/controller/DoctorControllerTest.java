package com.example.task3jpamanytomany.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.task3jpamanytomany.Mapper.DoctorMapperImpl;
import com.example.task3jpamanytomany.dao.entity.DoctorEntity;
import com.example.task3jpamanytomany.dao.repository.DoctorRepository;
import com.example.task3jpamanytomany.model.criteria.DoctorCriteriaRequest;
import com.example.task3jpamanytomany.model.request.DoctorRequest;
import com.example.task3jpamanytomany.model.response.DoctorResponse;
import com.example.task3jpamanytomany.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {DoctorController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DoctorControllerTest {
    @Autowired
    private DoctorController doctorController;

    @MockBean
    private DoctorService doctorService;

    /**
     * Method under test:
     * {@link DoctorController#getDoctors(DoctorCriteriaRequest, Pageable)}
     */
    @Test
    void testGetDoctors() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        when(doctorRepository.findAll(Mockito.<Specification<DoctorEntity>>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Page<DoctorResponse> actualDoctors = (new DoctorController(
                new DoctorService(doctorRepository, new DoctorMapperImpl()))).getDoctors(null, null);

        // Assert
        verify(doctorRepository).findAll(isA(Specification.class), (Pageable) isNull());
        assertTrue(actualDoctors.toList().isEmpty());
    }

    /**
     * Method under test:
     * {@link DoctorController#getDoctors(DoctorCriteriaRequest, Pageable)}
     */
    @Test
    void testGetDoctors2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        DoctorService doctorService = mock(DoctorService.class);
        PageImpl<DoctorResponse> pageImpl = new PageImpl<>(new ArrayList<>());
        when(doctorService.getDoctors(Mockito.<DoctorCriteriaRequest>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<DoctorResponse> actualDoctors = (new DoctorController(doctorService)).getDoctors(null, null);

        // Assert
        verify(doctorService).getDoctors(isNull(), isNull());
        assertTrue(actualDoctors.toList().isEmpty());
        assertSame(pageImpl, actualDoctors);
    }

    /**
     * Method under test: {@link DoctorController#getDoctor(Long)}
     */
    @Test
    void testGetDoctor() throws Exception {
        // Arrange
        DoctorResponse.DoctorResponseBuilder builderResult = DoctorResponse.builder();
        DoctorResponse buildResult = builderResult.dob(LocalDate.of(1970, 1, 1))
                .email("jane.doe@example.org")
                .id(1L)
                .name("Name")
                .phoneNumber("6625550144")
                .specialization("Specialization")
                .surname("Doe")
                .build();
        when(doctorService.getDoctor(Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/doctors/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(doctorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"dob\":\"1970-01-01\",\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"address\":null,\"surname\":\"Doe"
                                        + "\",\"phoneNumber\":\"6625550144\",\"specialization\":\"Specialization\"}"));
    }

    /**
     * Method under test:
     * {@link DoctorController#addDoctorPhoto(Long, MultipartFile)}
     */
    @Test
    void testAddDoctorPhoto() throws Exception {
        // Arrange
        DataInputStream contentStream = mock(DataInputStream.class);
        when(contentStream.readAllBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        doNothing().when(contentStream).close();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/doctors/{id}", "Uri Variables", "Uri Variables")
                .param("photo", String.valueOf(new MockMultipartFile("Name", contentStream)));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(doctorController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link DoctorController#delete(Long)}
     */
    @Test
    void testDelete() throws Exception {
        // Arrange
        Mockito.<ResponseEntity<?>>when(doctorService.deleteDoctor(Mockito.<Long>any())).thenReturn(null);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/v1/doctors");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("id", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(doctorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link DoctorController#edit(Long, DoctorRequest)}
     */
    @Test
    void testEdit() throws Exception {
        // Arrange
        DoctorResponse.DoctorResponseBuilder builderResult = DoctorResponse.builder();
        DoctorResponse buildResult = builderResult.dob(LocalDate.of(1970, 1, 1))
                .email("jane.doe@example.org")
                .id(1L)
                .name("Name")
                .phoneNumber("6625550144")
                .specialization("Specialization")
                .surname("Doe")
                .build();
        when(doctorService.updateDoctor(Mockito.<Long>any(), Mockito.<DoctorRequest>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/v1/doctors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        DoctorRequest.DoctorRequestBuilder nameResult = DoctorRequest.builder()
                .dob(null)
                .email("jane.doe@example.org")
                .name("Name");
        DoctorRequest buildResult2 = nameResult.patientIds(new ArrayList<>())
                .phoneNumber("6625550144")
                .specialization("Specialization")
                .surname("Doe")
                .build();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(buildResult2));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(doctorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"dob\":\"1970-01-01\",\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"address\":null,\"surname\":\"Doe"
                                        + "\",\"phoneNumber\":\"6625550144\",\"specialization\":\"Specialization\"}"));
    }

    /**
     * Method under test: {@link DoctorController#saveDoctor(DoctorRequest)}
     */
    @Test
    void testSaveDoctor() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/v1/doctors/add");
        DoctorRequest.DoctorRequestBuilder builderResult = DoctorRequest.builder();
        DoctorRequest.DoctorRequestBuilder nameResult = builderResult.dob(LocalDate.of(1970, 1, 1))
                .email("jane.doe@example.org")
                .name("Name");
        DoctorRequest buildResult = nameResult.patientIds(new ArrayList<>())
                .phoneNumber("6625550144")
                .specialization("Specialization")
                .surname("Doe")
                .build();
        MockHttpServletRequestBuilder requestBuilder = postResult.param("doctorRequest", String.valueOf(buildResult));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(doctorController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
