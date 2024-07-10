package com.example.task3jpamanytomany.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "doctors")
public class DoctorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    LocalDate dob;
    @Column(length = 35)
    String name;

    String email;
    String address;
    String surname;
    String phoneNumber;
    String specialization;

    @Lob
     String doctorPhoto;
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToMany()
    @JoinTable(name = "doctors_patients",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    List<PatientEntity> patients;
}
