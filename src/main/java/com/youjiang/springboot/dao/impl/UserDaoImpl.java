package com.youjiang.springboot.dao.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.youjiang.springboot.dao.UserDao;
import com.youjiang.springboot.mapper.UserMapperCustom;
import com.youjiang.springboot.mapper.generate.UserMapper;
import com.youjiang.springboot.po.User;
import com.youjiang.springboot.po.UserExample;
import com.youjiang.springboot.po.UserExample.Criteria;
import com.youjiang.springboot.utils.TimeUtils;

/**
 * Description: 用户dao层实现类
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月21日 下午3:32:06
 * @version 1.0
 */
@Repository
public class UserDaoImpl implements UserDao{
	
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserMapperCustom userMapperCustom;

	/**
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * unless 表示条件表达式成立的话不放入缓存
     */
	@Override
	@Cacheable(value = "countUserByState")
	public int countUserByState(Integer state) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		if(state != null){
			criteria.andStateEqualTo(state);
		}
		return userMapper.countByExample(example);
	}

	/**
     * @CachePut 应用到写数据的方法上，如新增/修改的方法，调用方法时会自动把相应的数据放入缓存
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     */
    @Override
    @CachePut(value = "user")
	public User insertUser(String nick) throws Exception {
		User user = new User();
		user.setUserNick(nick);
		user.setState(1);
		user.setCreateTime(TimeUtils.getNow());
		userMapperCustom.insertUserReturnId(user);
		System.out.println("userId....................."+user.getUserId());
        return user;
	}

	@Override
	@CachePut(value = "user")
	public User updateUser(int userId, Integer state, String nick) throws Exception {
		User user = new User();
		user.setUserId(userId);
		user.setUpdateTime(TimeUtils.getNow());
		if(state != null){
			user.setState(state);
		}
		if(nick != null){
			user.setUserNick(nick);
		}
		userMapper.updateByPrimaryKeySelective(user);
        return user;
	}
}
