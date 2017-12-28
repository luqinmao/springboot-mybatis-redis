package com.youjiang.springboot.service;

import javax.servlet.http.HttpSession;

/**
 * Description: 用户操作服务层接口
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月21日 上午9:43:48
 * @version 1.0
 */

public interface UserService {

	/**
	 * 根据状态查找用户分页列表数据
	 * @param state 状态码：1正常、0不显示
	 * @param currentPage 当前页码
	 * @param totalCount 当前页需要的条目数
	 * @return json数据，包含用户id、昵称、创建时间、更新时间、状态
	 * @throws Exception
	 * @author kwum
	 */
    String selectUserPageDataByState(Integer state, Integer currentPage, Integer totalCount) throws Exception;
	
	/**
	 * 新增用户
	 * @param nick 昵称
	 * @return json数据
	 * @throws Exception
	 * @author kwum
	 */
    String addUser(String nick) throws Exception;
	
	/**
	 * 更新用户
	 * @param userId 用户id
	 * @param state 状态码：1正常、0删除、-99不修改，可不传，默认-99
	 * @param nick 昵称，可不传，默认空
	 * @return json数据
	 * @throws Exception
	 * @author kwum
	 */
    String updateUser(int userId, Integer state, String nick) throws Exception;

    String login(String username, String password, String code, HttpSession session);
}
