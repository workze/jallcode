package com.ze.home.cronjob;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJob {
    @Scheduled(cron = "0 * 9 * * ?")
    public void doSomething(){
        System.out.println("I am running...");
    }
}
