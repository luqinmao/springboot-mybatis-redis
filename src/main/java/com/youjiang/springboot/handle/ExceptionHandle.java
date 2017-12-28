package com.youjiang.springboot.handle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youjiang.springboot.utils.JsonFormatUtils;

/**
 * Description: 异常处理
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月22日 下午4:02:40
 * @version 1.0
 */
@ControllerAdvice
public class ExceptionHandle {
	
	//创建日志对象
	private final static Logger log=LoggerFactory.getLogger(ExceptionHandle.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String exceptionHandle(Exception exception){
	    
	    //登陆异常，无需记录在日志，直接返回
	    if(StringUtils.equalsIgnoreCase("1000", exception.getMessage())){
	        return JsonFormatUtils.error(1000);
	    }
		
		log.error("系统异常", exception);
		return JsonFormatUtils.message(-1, exception.toString());
	}
}
