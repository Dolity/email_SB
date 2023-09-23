package com.iamnbty.training.backend.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    // 1 -> Second
    // 2 -> Minute
    // 3 -> Hour
    // 4 -> Day
    // 5 -> Month
    // 6 -> Year

    // Every minute UTC
    @Scheduled(cron = "0 * * * * *")
    public void testEveryMinute() {
       log.info("Hello, What's Up?");
    }

    // Every day at 00:00 (Thai)
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Bangkok")
    public void testEveryMidNight() {
        log.info("Hello, What's Up? -> 00:00");
    }



}
