package com.youjiang.springboot.service;

/**
 * Description: 线程操作服务层接口
 * @author Kwum
 * @date 2017年10月14日 下午5:49:28
 * @version 1.0
 */

public interface ThreadService {

    /**
     * 异步线程调用例子
     * @param threadName 线程名称
     * @return json数据
     * @throws Exception
     * @author kwum
     */
    void asyncThreadDemo(String threadName) throws Exception;
}
