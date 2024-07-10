package com.example.task3jpamanytomany.service;

import com.example.task3jpamanytomany.Mapper.PatientMapper;
import com.example.task3jpamanytomany.dao.entity.DoctorEntity;
import com.example.task3jpamanytomany.dao.entity.PatientEntity;
import com.example.task3jpamanytomany.dao.repository.DoctorRepository;
import com.example.task3jpamanytomany.dao.repository.PatientRepository;
import com.example.task3jpamanytomany.exception.PatientIdNotFoundException;
import com.example.task3jpamanytomany.model.request.PatientRequest;
import com.example.task3jpamanytomany.model.response.PatientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private  final DoctorRepository doctorRepository;
    public PatientResponse savePatient(PatientRequest patientRequest){

        log.info("ActionLog.savePatient start  " );

        PatientEntity patientEntity= patientMapper.toPatientEntity(patientRequest);
        List<DoctorEntity> doctorEntities=doctorRepository.findAllByIdIn(patientRequest.getDoctorIds());
        patientEntity.setDoctors(doctorEntities);
        PatientEntity patientEntitySave=patientRepository.save(patientEntity);

        log.info("ActionLog.savePatient end  " );


        return patientMapper.toPatientResponse(patientEntitySave);
    }

    public List<PatientResponse> getPatients(){

        log.info("ActionLog.getPatients start  " );

        List<PatientEntity> patients=patientRepository.findAll();

        log.info("ActionLog.getPatients end  " );

        return patientMapper.mapToListResponse(patients);
    }

    public PatientResponse updatePatient(Long id, PatientRequest patientRequest) {
        log.info("ActionLog.updatePatient start with id# "+id);

        PatientEntity patientEntity = patientRepository.findById(id).orElseThrow(() -> new PatientIdNotFoundException(HttpStatus.NOT_FOUND.name(), "Patient id not found "));
        patientMapper.mapForUpdate(patientEntity, patientRequest);
        patientEntity= patientRepository.save(patientEntity);

        log.info("ActionLog.updatePatient end");
        return patientMapper.toPatientResponse(patientEntity);
    }

    public HttpStatus deletePatient(Long id){

        log.info("ActionLog.deletePatient start");

        patientRepository.deleteById(id);

        log.info("ActionLog.deletePatient end");

        return HttpStatus.OK;
    }


}
