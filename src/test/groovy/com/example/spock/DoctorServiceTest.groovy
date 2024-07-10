package com.example.spock

import com.example.task3jpamanytomany.Mapper.DoctorMapper
import com.example.task3jpamanytomany.dao.entity.DoctorEntity
import com.example.task3jpamanytomany.dao.repository.DoctorRepository
import com.example.task3jpamanytomany.exception.DoctorIdNotFoundException
import com.example.task3jpamanytomany.model.criteria.DoctorCriteriaRequest
import com.example.task3jpamanytomany.model.request.DoctorRequest
import com.example.task3jpamanytomany.model.response.DoctorResponse
import com.example.task3jpamanytomany.service.DoctorService
import com.example.task3jpamanytomany.specification.DoctorSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class DoctorServiceTest extends Specification {

    private DoctorRepository doctorRepository

    private DoctorMapper doctorMapper

    private DoctorService doctorService
    //private EnhancedRandom random= EnhancedRandomBuilder

    def setup() {
        doctorRepository = Mock()
        doctorMapper = Mock()
        doctorService = new DoctorService(doctorRepository,doctorMapper)
    }

    def "saved doctor "() {
        given:
        def id = 1L
        def doctorRequest=DoctorRequest.builder()
                .name("Aytac")
                .surname("Ramazanli")
                .build()
        def doctorEntity = DoctorEntity.builder()
                .id(10L)
                .name("Aytac")
                .surname("Ramazanli")
                .build()

        def savedDoctorEntity = DoctorEntity.builder()
                .id(10L)
                .name("Aytac")
                .surname("Ramazanli")
                .build()

        def doctorResponse = DoctorResponse.builder()
                .name("Aytac")
                .surname("Ramazanli")
                .build()

        when:
        def result = doctorService.doctorSave(doctorRequest)

        then:
        1*doctorMapper.mapToDoctor(doctorRequest)>>doctorEntity
        1*doctorRepository.save(doctorEntity)>>savedDoctorEntity
        1*doctorMapper.mapToResponse(savedDoctorEntity)>>doctorResponse

        result==doctorResponse

    }





    def "getDoctor success"() {
        given:
        def id = 1L
        def doctorEntity = DoctorEntity.builder()
                .id(id)
                .name("Aytac")
                .surname("Ramazanli")
                .build()

        def doctorResponse = DoctorResponse.builder()
                .id(id)
                .name("Aytac")
                .surname("Ramazanli")
                .build()

        when:
        def result = doctorService.getDoctor(id)

        then:
        1 * doctorRepository.findById(id) >> Optional.of(doctorEntity)
        1 * doctorMapper.mapToResponse(doctorEntity) >> doctorResponse

        result == doctorResponse
    }

    def "getDoctor throws DoctorIdNotFoundException when doctor not found"() {
        given:
        def id = 999L

        when:
        doctorService.getDoctor(id)

        then:
        1 * doctorRepository.findById(id) >> Optional.empty()
        0 * doctorMapper.mapToResponse(_)
        def e = thrown(DoctorIdNotFoundException)
        e.message == "Doctor id not found"
    }




    def "UpdateDoctor"() {
        given:
        def id = 1L
        def doctorEntity = DoctorEntity.builder()
                .name("Lala")
                .surname("Ahmedova")
                .build()


        def doctorRequest = DoctorRequest.builder()
                .name("Lala")
                .surname("Ahmadova")
                .build()


        when:
        def result = doctorService.updateDoctor(id, doctorRequest)

        then:
        1 * doctorRepository.findById(id) >> Optional.of(doctorEntity)
        1 * doctorMapper.mapForUpdate(_, _) >> { DoctorEntity entity, DoctorRequest request ->
            entity.setName(request.getName())
            entity.setSurname(request.getSurname())
        }
        1 * doctorRepository.save(doctorEntity) >> doctorEntity

        result == doctorMapper.mapToResponse(doctorEntity)

    }

    def "DeleteDoctor"() {
        given:
        def id = 1L
        def doctorEntity = DoctorEntity.builder()
                .name("Sefa")
                .surname("Veliyeva")
                .build()


        when:
        def result = doctorService.deleteDoctor(id)

        then:
        1 * doctorRepository.deleteById(id)

        result != null


    }
}
