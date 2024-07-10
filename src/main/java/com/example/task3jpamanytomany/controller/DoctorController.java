package com.example.task3jpamanytomany.controller;

import com.example.task3jpamanytomany.model.criteria.DoctorCriteriaRequest;
import com.example.task3jpamanytomany.model.request.DoctorRequest;
import com.example.task3jpamanytomany.model.response.DoctorResponse;

import com.example.task3jpamanytomany.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/add")
    public DoctorResponse saveDoctor(@Valid @RequestPart("doctor") DoctorRequest doctorRequest)  {
        return doctorService.doctorSave(doctorRequest);
    }



    @GetMapping
    public Page<DoctorResponse> getDoctors(@RequestBody DoctorCriteriaRequest request,
                                           @PageableDefault(size = 10, sort = "dob") Pageable pageable) {

        return doctorService.getDoctors(request, pageable);
    }


    @PostMapping("/{id}")
    public void addDoctorPhoto(@PathVariable Long id,
                               @RequestPart("photo") MultipartFile photo) throws IOException {
        doctorService.addDoctorPhoto(id, photo);
    }

    @PutMapping("/{id}")
    public DoctorResponse edit(@PathVariable Long id, @RequestBody DoctorRequest request) {
        return doctorService.updateDoctor(id, request);
    }

    @GetMapping("/{id}")
    public DoctorResponse getDoctor(@PathVariable Long id) {
        return doctorService.getDoctor(id);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        doctorService.deleteDoctor(id);
    }


}