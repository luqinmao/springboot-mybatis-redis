package com.youjiang.springboot.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * Description: 自定义的JobFactory
 * 为了在JOB中使用Spring管理的Bean，需要重新定义一个Job Factory
 * @author Kwum
 * @date 2017年11月6日 下午4:42:35
 * @version 1.0
 */

@Component
public class MyJobFactory extends AdaptableJobFactory {  
    
    @Autowired  
    private AutowireCapableBeanFactory capableBeanFactory;  
  
    @Override  
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {  
        //调用父类的方法  
        Object jobInstance = super.createJobInstance(bundle);  
        //进行注入  
        capableBeanFactory.autowireBean(jobInstance);  
        return jobInstance;  
    }  
      
}
