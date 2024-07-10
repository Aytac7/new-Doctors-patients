package com.example.task3jpamanytomany.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="patients")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Id
     Long id;
     int age;
     Date dob;
     String name;
     String gender;
     String surname;
     String address;
     String bloodType;
     String phoneNumber;
     @CreationTimestamp
     LocalDateTime createdAt;
     @UpdateTimestamp
     LocalDateTime updateAt;

     @ManyToMany()
     @JoinTable(name = "doctors_patients",
             joinColumns = @JoinColumn(name = "patient_id"),
             inverseJoinColumns = @JoinColumn(name = "doctor_id"))
     List<DoctorEntity>doctors;
}
