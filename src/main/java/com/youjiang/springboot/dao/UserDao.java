package com.youjiang.springboot.dao;

import com.youjiang.springboot.po.User;

/**
 * Description: 用户dao层接口
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月21日 下午3:31:01
 * @version 1.0
 */

public interface UserDao {

	/**
	 * 根据状态统计用户数量
	 * @param state 状态码：1正常、0删除
	 * @return 该状态的用户数量
	 * @throws Exception
	 * @author kwum
	 */
	public int countUserByState(Integer state) throws Exception;
	
	/**
	 * 新增用户
	 * @param nick 昵称
	 * @throws Exception
	 * @author kwum
	 */
	public User insertUser(String nick) throws Exception;
	
	/**
	 * 更新用户
	 * @param userId 用户id
	 * @param state 状态码：1正常、0删除、-99不修改，可不传，默认-99
	 * @param nick 昵称，可不传，默认空
	 * @throws Exception
	 * @author kwum
	 */
	public User updateUser(int userId, Integer state, String nick) throws Exception;
}
