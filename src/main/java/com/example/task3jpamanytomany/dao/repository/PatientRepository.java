package com.example.task3jpamanytomany.dao.repository;

import com.example.task3jpamanytomany.dao.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    List<PatientEntity> findAllByIdIn(List<Long> id);
}
