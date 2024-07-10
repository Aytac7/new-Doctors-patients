package com.example.task3jpamanytomany.specification;

import com.example.task3jpamanytomany.dao.entity.DoctorEntity;
import com.example.task3jpamanytomany.model.criteria.DoctorCriteriaRequest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;





public class DoctorSpecification implements Specification<DoctorEntity> {

    public static Specification<DoctorEntity> getDoctorsByCriteria(DoctorCriteriaRequest doctorCriteriaRequest){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates=new ArrayList<>();

            if(doctorCriteriaRequest.getName() !=null && !doctorCriteriaRequest.getName().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + doctorCriteriaRequest ));
            }
            if(doctorCriteriaRequest.getDobFrom()!=null && doctorCriteriaRequest.getDobTo()!=null){
                predicates.add(criteriaBuilder.between(root.get("dob"),
                        doctorCriteriaRequest.getDobFrom(), doctorCriteriaRequest.getDobTo()));
            }
//            if (doctorCriteriaRequest.getDobFrom() != null && doctorCriteriaRequest.getDobTo() != null) {
//                predicates.add(criteriaBuilder.between(root.get("dob"),
//                        doctorCriteriaRequest.getDobFrom(), doctorCriteriaRequest.getDobTo()));
//            } else if (doctorCriteriaRequest.getDobFrom() != null) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dob"), doctorCriteriaRequest.getDobFrom()));
//            } else if (doctorCriteriaRequest.getDobTo() != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dob"), doctorCriteriaRequest.getDobTo()));
//            }


            if (doctorCriteriaRequest.getEmail() != null && !doctorCriteriaRequest.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + doctorCriteriaRequest.getEmail().toLowerCase() + "%"));
            }

            if (doctorCriteriaRequest.getAddress() != null && !doctorCriteriaRequest.getAddress().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + doctorCriteriaRequest.getAddress().toLowerCase() + "%"));
            }
            if (doctorCriteriaRequest.getSurname() != null && !doctorCriteriaRequest.getSurname().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + doctorCriteriaRequest.getSurname().toLowerCase() + "%"));
            }
            if (doctorCriteriaRequest.getPhoneNumber() != null && !doctorCriteriaRequest.getPhoneNumber().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + doctorCriteriaRequest.getPhoneNumber().toLowerCase() + "%"));
            }
            if (doctorCriteriaRequest.getSpecialization() != null && !doctorCriteriaRequest.getSpecialization().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("specialization")), "%" + doctorCriteriaRequest.getSpecialization().toLowerCase() + "%"));
            }
            if (doctorCriteriaRequest.getPatientIds() != null && !doctorCriteriaRequest.getPatientIds().isEmpty()) {
                predicates.add(root.join("patients").get("id").in(doctorCriteriaRequest.getPatientIds()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public  Specification<DoctorEntity> and(Specification<DoctorEntity> other){
        return Specification.super.and(other);
    }

    @Override
    public Specification<DoctorEntity> or(Specification<DoctorEntity> other) {
        return Specification.super.or(other);
    }

    @Override
    public jakarta.persistence.criteria.Predicate toPredicate(Root<DoctorEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
