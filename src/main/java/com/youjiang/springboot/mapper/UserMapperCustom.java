package com.youjiang.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import com.youjiang.springboot.po.User;
import com.youjiang.springboot.utils.page.Pager;

/**
 * Description: 用户自定义mapper接口
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月21日 下午4:13:13
 * @version 1.0
 */

public interface UserMapperCustom {

    /**
     * 查找用户列表（分页查找）
     * @param state 用户状态
     * @param pager
     * @return 用户列表
     * @author kwum
     */
    @Cacheable(value = "usersPageData")
    public List<User> pageUserList(@Param("state")Integer state, @Param("pager")Pager<User> pager);
    
    /**
     * 新增用户返回用户id，在对象中获取
     * @param record 用户对象
     * @return
     * @throws Exception
     * @author kwum
     */
    public Integer insertUserReturnId(User record) throws Exception;
}
