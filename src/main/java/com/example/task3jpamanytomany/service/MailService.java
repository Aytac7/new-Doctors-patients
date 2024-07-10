package com.example.task3jpamanytomany.service;

import com.example.task3jpamanytomany.dao.entity.DoctorEntity;
import com.example.task3jpamanytomany.dao.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final DoctorRepository doctorRepository;


    private   final  JavaMailSender javaMailSender;

    public void notifyAboutBirtDate(){
        log.info("ActionLog.notifyAbout Birthdate.start");

        List<DoctorEntity> allDoctors=doctorRepository.findAll();
        List<DoctorEntity> birtDateDoctors=allDoctors.stream()
                .filter(e->e.getDob().getMonth().equals(LocalDate.now().getMonth())
                        &&(e.getDob().getDayOfMonth()==(LocalDate.now().plusDays(1).getDayOfMonth())))
                .toList();
        for(DoctorEntity doctor: birtDateDoctors){
            List<String> list = allDoctors.stream()
                    .filter(e -> !e.getId().equals(doctor.getId()))
                    .map(DoctorEntity::getEmail)
                    .toList();
            sendMail(list,doctor.getName());
        }
        log.info("ActionLog.notifyAboutBirtDate.end");
    }

    private  void  sendMail(List<String> cc, String name) {
       log.info("ActionLog.sendMail.start for {}", name);
        for (String to : cc) {
            log.info("ActionLog.sendMail.start for {}", name);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("aytacr67@gmail.com");
            message.setTo(to);
            message.setSubject("BirthDate of " + name);
            message.setText("Salam həmkarlar sabah dəyərli həkimimiz - " + name + " ad günüdür." +
                    "Gəlin birlikdə sevincinə şərik olaq");
            javaMailSender.send(message);
            log.info("ActionLog.sendMail.start for {}", name);
        }
    }


}
