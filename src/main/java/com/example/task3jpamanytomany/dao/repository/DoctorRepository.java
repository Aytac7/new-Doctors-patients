package com.example.task3jpamanytomany.dao.repository;

import com.example.task3jpamanytomany.dao.entity.DoctorEntity;
import com.example.task3jpamanytomany.dao.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity,Long>,
         PagingAndSortingRepository<DoctorEntity,Long>, JpaSpecificationExecutor<DoctorEntity> {

    List<DoctorEntity> findAllByIdIn(List<Long> id);
    Optional<DoctorEntity> findByName(String name);

    Optional<DoctorEntity> findByEmail(String email);



}
