package com.youjiang.springboot.quartz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youjiang.springboot.service.UserService;

/**
 * Description: 定时任务1
 * @author Kwum
 * @date 2017年11月6日 上午10:45:55
 * @version 1.0
 */
@Component
public class Job1 implements Job{
    
    @Autowired
    private UserService userService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("quartz1 run: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        try {
            System.out.println("quartz1 run: " + userService.selectUserPageDataByState(1, 1, 10).toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
