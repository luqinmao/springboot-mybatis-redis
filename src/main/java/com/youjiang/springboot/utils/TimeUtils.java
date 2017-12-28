package com.youjiang.springboot.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description: 时间工具类
 * @author Kwum
 * @date 2017年11月6日 上午10:45:55
 * @version 1.0
 */
public class TimeUtils {
    
    //时间格式：yyyy-MM-dd HH:mm:ss
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //日期格式：yyyy-MM-dd
    private static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
    /**
     * Description:距离当前时间多少天的日期
     * @author feng
     * @param distance 
     * @return yyyy-MM-dd 天数，如果是正数为当前时间的后多少天，反之是当前时间前，eg:2017-11-07
     */
    public static String distanceOfDate(int distance) {
        return LocalDate.now().plusDays(distance).format(formatterDate);
    }
    
    /**
     * 获取当前时间
     * @return eg:2017-11-07 10:36:26
     * @author kwum
     */
    public static String getNow(){
        return LocalDateTime.now().format(formatter);
    }
    
    public static void main(String[] args) {
        System.out.println(getNow());
    }
}