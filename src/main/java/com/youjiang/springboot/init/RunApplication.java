package com.youjiang.springboot.init;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description: springboot项目启动器（需与controller放在同一个包中）
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月19日 下午5:19:01
 * @version 1.0
 */
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.youjiang.springboot.controller", 
        "com.youjiang.springboot.pojo", 
		"com.youjiang.springboot.service", 
		"com.youjiang.springboot.dao", 
		"com.youjiang.springboot.filter",
		"com.youjiang.springboot.handle", 
//		"com.youjiang.springboot.config", 
//		"com.youjiang.springboot.quartz",
		"com.youjiang.springboot.utils.page", 
//		"com.youjiang.springboot.timer"
		})
@MapperScan("com.youjiang.springboot.mapper")
public class RunApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}
}
