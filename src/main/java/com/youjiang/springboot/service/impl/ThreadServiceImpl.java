package com.youjiang.springboot.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youjiang.springboot.service.ThreadService;


/**
 * Description: 线程操作服务层实现类
 * @author Kwum
 * @date 2017年10月14日 下午5:50:11
 * @version 1.0
 */

@Service
@Transactional
public class ThreadServiceImpl implements ThreadService{

    @Override
    @Async
    public void asyncThreadDemo(String threadName) throws Exception {
        final long time = 5000;
        System.out.println(threadName + " is working.");
        Thread.sleep(time);
        System.out.println(threadName + " is done.");
    }
}
