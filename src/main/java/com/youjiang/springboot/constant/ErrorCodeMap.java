package com.youjiang.springboot.constant;

import java.util.HashMap;
import java.util.Map;


/**
 * Description: 错误码map集合
 * Company: youjiang
 * @author Kwum
 * @date 2017年3月21日 上午11:31:36
 * @version 1.0
 */

public class ErrorCodeMap {

	public static final Map<String, String> MAP = new HashMap<String, String>();
	
	static{
	    MAP.put("1000", "请重新登录");
	    MAP.put("1001", "登录失败，验证码错误");
	    MAP.put("1002", "登录失败，账号不存在");
	    MAP.put("1003", "登录失败，密码错误");
	}
}
