package com.example.task3jpamanytomany.scheduled;

import com.example.task3jpamanytomany.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class Scheduler {
    private final MailService mailService;

    //@Scheduled(fixedDelay = 10000000)
    public void test(){
        log.info("ActionLog.test.start");

        mailService.notifyAboutBirtDate();

        log.info("ActionLog.test.end");
    }
}

