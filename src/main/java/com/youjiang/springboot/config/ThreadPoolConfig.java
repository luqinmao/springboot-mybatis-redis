package com.youjiang.springboot.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Description: 线程池配置类
 * @author Kwum
 * @date 2017年10月14日 下午5:05:52
 * @version 1.0
 */

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean
    public Executor defaultThreadPool() {
      ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
      //线程池最大线程数
      executor.setPoolSize(2);
      //线程优先级
      executor.setThreadPriority(1);
      //线程名称前缀
      executor.setThreadNamePrefix("asyncThread");
      executor.initialize();
      return executor;
    }
}
