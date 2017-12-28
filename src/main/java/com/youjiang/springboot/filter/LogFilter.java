package com.youjiang.springboot.filter;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Description: 日志过滤器
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月22日 下午2:29:41
 * @version 1.0
 */
@Aspect
@Component
public class LogFilter {
	
	//创建日志对象
	private final static Logger log = LoggerFactory.getLogger(LogFilter.class);	
	
	@Pointcut("execution(public * com.youjiang.springboot.controller.*.*(..))")
	public void log(){	}

	/**
	 * 请求服务器之前的操作
	 * @author kwum
	 */
	@Before("log()")
	public void logBeforeRequest(JoinPoint joinPoint){
		
		//请求进入服务器
		log.info("request enter");
		
		//获取请求
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		//url
		log.info("url: " + request.getRequestURL());
		
		//method
		log.info("method: " + request.getMethod());
		
		//ip
		log.info("ip: " + request.getRemoteAddr());
		
		//class method
		log.info("class.method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		
		//args
//		for(Object object : joinPoint.getArgs()){
//			log.info("args: " + object.toString());
//		}
	}
	
	/**
	 * 请求服务器之后的操作
	 * @author kwum
	 */
	@After("log()")
	public void logAfterRequest(){
		
	}
	
	
	/**
	 * 服务器响应之后的操作
	 * @author kwum
	 */
	@AfterReturning(returning = "result", pointcut = "log()")
	public void logAfterResponse(String result){
		
		//response
		log.info("response: " + result);
		
		//服务器响应结束
		log.info("response complete");
	}
}
