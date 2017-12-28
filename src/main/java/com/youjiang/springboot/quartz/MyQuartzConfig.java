package com.youjiang.springboot.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Description: quartz配置类，配置调动参数
 * @author Kwum
 * @date 2017年11月6日 下午3:55:34
 * @version 1.0
 */

@Component
public class MyQuartzConfig {
    
    @Autowired
    @Qualifier("schedulerFactoryBean")
    SchedulerFactoryBean schedulerFactoryBean;
    
    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        startJob1(scheduler);
        startJob2(scheduler);
    }
    
    private void startJob1(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(Job1.class).withIdentity("job1", "groupJob1").build(); 
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?"); 
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("triggerJob1", "groupJob1") .withSchedule(scheduleBuilder).build(); 
        scheduler.scheduleJob(jobDetail,cronTrigger); 
    }
    
    private void startJob2(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(Job2.class).withIdentity("job2", "groupJob2").build(); 
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?"); 
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("triggerJob2", "groupJob2") .withSchedule(scheduleBuilder).build(); 
        scheduler.scheduleJob(jobDetail,cronTrigger); 
    }
}


