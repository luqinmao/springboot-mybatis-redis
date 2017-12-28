package com.youjiang.springboot.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.youjiang.springboot.constant.PublicUri;
import com.youjiang.springboot.constant.SessionKey;

/**
 * Description: 登录拦截过滤器
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月22日 下午2:29:41
 * @version 1.0
 */
@Aspect
//@Component
public class LoginFilter {
    
	@Pointcut("execution(public * com.youjiang.springboot.controller.*.*(..))")
	public void log(){	}

	/**
	 * 请求服务器之前的操作
	 * @author kwum
	 * @throws Exception 
	 */
	@Before("log()")
	public void logBeforeRequest(JoinPoint joinPoint) throws Exception{
		
		//获取请求
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		//用于判断是否需要做登录校验
		boolean isNeedLogin = true;
		
		//请求存在于公开地址，则不需要做登录校验
		for(String uri : PublicUri.LIST){
		    if(uri.equalsIgnoreCase(request.getRequestURI())){
		        isNeedLogin = false;
		    }
		}
		
		//登录校验
		if(isNeedLogin){
		    
		    HttpSession session = request.getSession();
		    Integer sessionUserId = (Integer) session.getAttribute(SessionKey.USER_ID);
            if(null == sessionUserId){
                
                //需要在exceptionHandle中处理这个自定义异常
                throw new RuntimeException("1000");
            }
		}
	}
}
