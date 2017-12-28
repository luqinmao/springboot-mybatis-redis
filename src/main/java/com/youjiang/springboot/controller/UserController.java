package com.youjiang.springboot.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youjiang.springboot.constant.SessionKey;
import com.youjiang.springboot.mapper.generate.UserMapper;
import com.youjiang.springboot.service.UserService;
import com.youjiang.springboot.utils.AESUtils;
import com.youjiang.springboot.utils.HtmlCaptchaUtils;
import com.youjiang.springboot.utils.JsonFormatUtils;
import com.youjiang.springboot.utils.RSAUtils;

import net.sf.json.JSONObject;

/**
 * Description: 用户操作控制器
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月21日 上午9:30:58
 * @version 1.0
 */
@RestController
@RequestMapping(value = "springboot", produces="application/json;charset=UTF-8")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private UserMapper userMapper;
	
	/**
	 * 根据状态查看用户列表
	 * @param state
	 * @param currentPage 当前页码
	 * @param totalCount 当前页需要的条目数
	 * @return json数据，包含用户id、昵称、创建时间、更新时间、状态
	 * @throws Exception
	 * @author kwum
	 */
	@GetMapping("users")
	public String showUserList(
			@RequestParam(value="state", required=false)Integer state,
			@RequestParam(value="currentPage", required=false, defaultValue="1")Integer currentPage,
			@RequestParam(value="totalCount", required=false, defaultValue="10")Integer totalCount) throws Exception{
		
		return userService.selectUserPageDataByState(state, currentPage, totalCount);
	}
	
	/**
	 * 根据id查看用户详情
	 * @param userId
	 * @return json数据，包含用户id、昵称、创建时间、更新时间、状态
	 * @throws Exception
	 * @author kwum
	 */
	@GetMapping("user/{id}")
	public String showUserInfo(
			@PathVariable(value = "id")Integer userId) throws Exception{
		
		return JsonFormatUtils.success(JSONObject.fromObject(userMapper.selectByPrimaryKey(userId)));
	}
	
	/**
	 * 新增用户
	 * @param nick 昵称
	 * @return json数据
	 * @throws Exception
	 * @author kwum
	 */
	@PostMapping("user")
	public String insertUser(
			@RequestParam(value="nick")String nick) throws Exception{
		
		return userService.addUser(nick);
	}
	
	/**
	 * 更新用户
	 * @param userId 用户id
	 * @param state
	 * @param nick 昵称，可不传，默认空
	 * @return json数据
	 * @throws Exception
	 * @author kwum
	 */
	@PutMapping("user")
	public String updateUser(
			@RequestParam(value="userId")Integer userId,
			@RequestParam(value="state", required=false)Integer state,
			@RequestParam(value="nick", required=false)String nick) throws Exception{
		
		return userService.updateUser(userId, state, nick);
	}
	
	/**
     * 用户登录
     * @param session
     * @param username 账号
     * @param password 密码
     * @param code 验证码
     * @param key 秘钥
     * @return
     * @throws Exception
     * @author kwum
     */
    @PostMapping("login")
    public String login(HttpSession session,
            @RequestParam(value = "username")String username,
            @RequestParam(value = "password")String password,
            @RequestParam(value = "code")String code,
            @RequestParam(value = "key")String key) throws Exception{
        
        key = RSAUtils.decryptByPrivateKey(key);
        password = AESUtils.decryptData(key, password);
        return userService.login(username, password, code, session);
    }
    
    /**
     * 退出登录
     * @param session
     * @return
     * @throws Exception
     * @author kwum
     */
    @PostMapping("logout")
    public String logout(HttpSession session) throws Exception{
        
        session.invalidate();
        return JsonFormatUtils.success();
    }
    
    /**
     * 获取验证码
     * @param session
     * @param response
     * @throws IOException
     * @author kwum
     */
    @RequestMapping("code")
    public void getCode(HttpSession session, HttpServletResponse response) throws Exception{
        String code = HtmlCaptchaUtils.generateVerifyCode(4);
        session.setAttribute(SessionKey.HTML_CAPTCHA, code);
        HtmlCaptchaUtils.outputImage(200, 80, response.getOutputStream(), code);
    }
}
