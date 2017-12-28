package com.youjiang.springboot.init;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Description: 以war包形式发布时，需要继承SpringBootServletInitializer复写其中的configure方法
 * @author Kwum
 * @date 2017年11月27日 上午11:26:43
 * @version 1.0
 */

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RunApplication.class);
    }
}
