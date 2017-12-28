package com.youjiang.springboot.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Description: quart配置类
 * 注册spring-boot启动完成事件监听，用于启动job任务
 * @author Kwum
 * @date 2017年11月6日 下午2:12:18
 * @version 1.0
 */

@Configuration
public class StartQuartzListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    public MyQuartzConfig myQuartz;
    @Autowired
    private MyJobFactory myJobFactory;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) { 
        try { 
            myQuartz.scheduleJobs(); 
        } catch (SchedulerException e) { 
            e.printStackTrace(); 
        }
    }
    
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        return schedulerFactoryBean;
    }
}