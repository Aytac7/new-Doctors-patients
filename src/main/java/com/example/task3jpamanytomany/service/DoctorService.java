package com.example.task3jpamanytomany.service;

import com.example.task3jpamanytomany.Mapper.DoctorMapper;
import com.example.task3jpamanytomany.dao.entity.DoctorEntity;
import com.example.task3jpamanytomany.dao.repository.DoctorRepository;
import com.example.task3jpamanytomany.exception.DoctorIdNotFoundException;
import com.example.task3jpamanytomany.exception.NotFoundException;
import com.example.task3jpamanytomany.model.criteria.DoctorCriteriaRequest;
import com.example.task3jpamanytomany.model.request.DoctorRequest;
import com.example.task3jpamanytomany.model.response.DoctorResponse;
import com.example.task3jpamanytomany.specification.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorResponse doctorSave(DoctorRequest doctorRequest)  {
        log.info("ActionLog.doctorSave start");

        DoctorEntity doctorEntity = doctorMapper.mapToDoctor(doctorRequest);
        DoctorEntity saveDoctorEntity = doctorRepository.save(doctorEntity);

        log.info("ActionLog.doctorSave end");

        return doctorMapper.mapToResponse(saveDoctorEntity);
    }

    public Page<DoctorResponse> getDoctors(DoctorCriteriaRequest doctorCriteriaRequest , Pageable pageable) {

        log.info("ActionLog.getDoctors start  " );

        Specification<DoctorEntity> spec = DoctorSpecification.getDoctorsByCriteria(doctorCriteriaRequest);
        Page<DoctorEntity> doctors = doctorRepository.findAll(spec, pageable);

        log.info("ActionLog.getDoctors end  " );

        return doctors.map(doctorMapper::mapToResponse);

    }

    public void addDoctorPhoto(long id, MultipartFile photo) throws IOException {

        log.info("ActionLog.addDoctorPhoto start  " );

        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,"not found"));
        doctorEntity.setDoctorPhoto(Base64.getEncoder().encodeToString(photo.getBytes()));
        doctorRepository.save(doctorEntity);

        log.info("ActionLog.addDoctorPhoto end  " );
    }



    public DoctorResponse updateDoctor(Long id, DoctorRequest doctorRequest) {
        log.info("ActionLog.updateDoctor start with id# " + id);

        DoctorEntity doctorEntity = doctorRepository.findById(id).orElseThrow(() -> new DoctorIdNotFoundException(HttpStatus.NOT_FOUND.name(), "Doctor id not found "));
        doctorMapper.mapForUpdate(doctorEntity, doctorRequest);
        doctorEntity = doctorRepository.save(doctorEntity);

        log.info("ActionLog.updateDoctor end");
        return doctorMapper.mapToResponse(doctorEntity);
    }

    public DoctorResponse getDoctor(Long id) {
        log.info("ActionLog.getDoctor start with id#" + id);

        DoctorEntity doctorEntity = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorIdNotFoundException(HttpStatus.NOT_FOUND.name(), "Doctor id not found"));

        var result = doctorMapper.mapToResponse(doctorEntity);
        log.info("ActionLog.getDoctor end");
        return result;

    }


    public ResponseEntity<?> deleteDoctor(Long id) {
        log.info("ActionLog.deleteDoctor start with id#" + id);

        doctorRepository.deleteById(id);

        log.info("ActionLog.deleteDoctor end");

        return ResponseEntity.ok("successfully");
    }

}
//contollerde method name status adi