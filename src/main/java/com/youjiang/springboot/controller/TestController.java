package com.youjiang.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youjiang.springboot.mapper.generate.UserMapper;
import com.youjiang.springboot.pojo.KwumProperties;
import com.youjiang.springboot.service.ThreadService;
import com.youjiang.springboot.utils.JsonFormatUtils;

/**
 * Description: 测试控制器
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月19日 下午5:19:01
 * @version 1.0
 */

@RestController
@RequestMapping(value = "springboot", produces="application/json;charset=UTF-8")
public class TestController {
	
	@Resource
	private KwumProperties kwumProperties;	//读取yml文件中的自定义参数
	@Resource
	private UserMapper userMapper;
	@Resource
	private ThreadService threadService;
	
	@RequestMapping(value="hello")
	public String hello() throws Exception{
		
		//获取自定义参数
//		System.out.println("Hello Spring Boot! " + kwumProperties.getHeight());
		
		//获取user
//		System.out.println(userMapper.selectByExample(null).get(0).getUserNick());
		
		//模拟异常
//		System.out.println(1/0);
		
		//hello world!
		return "Hello Spring Boot!";
	}
	
	@RequestMapping(value="helloworld")
    public String helloworld() throws Exception{
        
        //hello world!
        return JsonFormatUtils.success("Hello World, Spring Boot!");
    }
	
	/**
	 * 测试线程池
	 * @param name 线程名称
	 * @return
	 * @throws Exception
	 * @author kwum
	 */
	@RequestMapping(value="thread", method = RequestMethod.POST)
    public String threadTest(@RequestParam(value="name")String name) throws Exception{
	    threadService.asyncThreadDemo(name);
	    return JsonFormatUtils.success("线程" + name + "已启用,正在处理...").toString();
	}
}
