package com.youjiang.springboot.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 公开地址，在此类声明过的公开地址均不会被登陆拦截
 * @author Kwum
 * @date 2017年12月18日 下午3:41:09
 * @version 1.0
 */

public class PublicUri {

    public static final List<String> LIST = new ArrayList<>();
    
    static{
        LIST.add("/springboot/helloWorld");
        LIST.add("/springboot/login");
    }
}
