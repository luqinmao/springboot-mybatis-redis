package com.youjiang.springboot.constant;


/**
 * Description: 用于定义保存在session中的值
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月20日 下午2:30:42
 * @version 1.0
 */

public interface SessionKey {

    //用户id
    public static final String USER_ID = "SESSION_USER_ID";
    
    //用户网页登录的验证码
    public static final String HTML_CAPTCHA = "SESSION_USER_LOGIN_HTML_CAPTCHA";
}
