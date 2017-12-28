package com.youjiang.springboot.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description: 简易定时器
 * @author Kwum
 * @date 2017年12月18日 下午2:36:19
 * @version 1.0
 */
@Component
public class TimerTask {

    @Scheduled(cron = "*/5 * * * * ?")
    public void job() throws Exception {
        System.out.println("Timer is working.");
    }
}
