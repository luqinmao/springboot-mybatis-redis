package com.youjiang.springboot.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youjiang.springboot.constant.SessionKey;
import com.youjiang.springboot.constant.UserState;
import com.youjiang.springboot.dao.UserDao;
import com.youjiang.springboot.mapper.UserMapperCustom;
import com.youjiang.springboot.mapper.generate.UserMapper;
import com.youjiang.springboot.po.User;
import com.youjiang.springboot.po.UserExample;
import com.youjiang.springboot.service.UserService;
import com.youjiang.springboot.utils.JsonFormatUtils;
import com.youjiang.springboot.utils.page.OrderType;
import com.youjiang.springboot.utils.page.Pager;

import net.sf.json.JSONObject;

/**
 * Description: 用户操作服务层实现类
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月21日 上午9:44:21
 * @version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserMapperCustom userMapperCustom;

	@Override
	public String selectUserPageDataByState(Integer state, Integer currentPage, Integer pageSize) throws Exception {
	    Pager<User> pager = new Pager<>(currentPage, pageSize, "u.user_id", OrderType.DESC);
        List<User> pageList = userMapperCustom.pageUserList(state, pager);
        pager.setList(pageList);
        JSONObject json = JSONObject.fromObject(pager);
        json.put("typeList", UserState.getList());
        return JsonFormatUtils.success(json);
	}

	@Override
	public String addUser(String nick) throws Exception {
		
		//插入用户数据
		User user = userDao.insertUser(nick);
		
		return JsonFormatUtils.success(user.getUserId());
	}

	@Override
	public String updateUser(int userId, Integer state, String nick) throws Exception {
		
		//更新用户数据
		userDao.updateUser(userId, state, nick);
		
		return JsonFormatUtils.success();
	}

    @Override
    public String login(String username, String password, String code, HttpSession session) {
        
        //校验验证码
        String checkCode = (String) session.getAttribute(SessionKey.HTML_CAPTCHA);
        if(!StringUtils.equalsIgnoreCase(code, checkCode)){
            return JsonFormatUtils.error(1001);
        }
        
        //校验账号
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNickEqualTo(username);
        List<User> list = userMapper.selectByExample(userExample);
        if(list == null || list.isEmpty()){
            return JsonFormatUtils.error(1002);
        }
        
        //校验密码
        User user = list.get(0);
        if(!StringUtils.equalsIgnoreCase(password, user.getState().toString())){
            return JsonFormatUtils.error(1003);
        }
        
        //登录成功，保存信息到session
        session.setAttribute(SessionKey.USER_ID, user.getUserId());
        
        return JsonFormatUtils.success();
    }
}
