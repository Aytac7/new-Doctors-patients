package com.example.task3jpamanytomany.Mapper;

import com.example.task3jpamanytomany.dao.entity.DoctorEntity;

import com.example.task3jpamanytomany.model.request.DoctorRequest;
import com.example.task3jpamanytomany.model.response.DoctorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {
  //  DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    DoctorEntity mapToDoctor(DoctorRequest doctorRequest);

    DoctorResponse mapToResponse(DoctorEntity doctorEntity);

    List<DoctorResponse> mapToListResponse(List<DoctorEntity> doctorEntityList);

    default Page<DoctorResponse> mapToListPageResponse(Page<DoctorEntity> doctorEntityPage) {
        List<DoctorResponse> doctorResponses = new ArrayList<>();

        for (DoctorEntity doctorEntity : doctorEntityPage.getContent()) {
            doctorResponses.add(mapToResponse(doctorEntity));
        }

        return new PageImpl<>(doctorResponses,
                doctorEntityPage.getPageable(),
                doctorEntityPage.getTotalElements()
        );
    }


    void mapForUpdate(@MappingTarget DoctorEntity doctorEntity, DoctorRequest doctorRequest);
}


