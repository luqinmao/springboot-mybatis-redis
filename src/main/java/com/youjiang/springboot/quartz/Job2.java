package com.youjiang.springboot.quartz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Description: 定时任务2
 * @author Kwum
 * @date 2017年11月6日 上午10:45:55
 * @version 1.0
 */

public class Job2 implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("quartz2 run: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
