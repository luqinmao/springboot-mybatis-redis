package com.youjiang.springboot.utils;

import com.youjiang.springboot.constant.ErrorCodeMap;

import net.sf.json.JSONObject;

/**
 * Description: Json格式返回工具类
 * Company: youjiang
 * @author Kwum
 * @date 2017年3月21日 上午11:12:54
 * @version 1.0
 */

public class JsonFormatUtils {

	/**
	 * 状态码
	 */
	private static final String JSONCODE = "code";
	
	/**
	 * 消息内容
	 */
	private static final String JSONMSG = "msg";
	
	/**
	 * 数据名
	 */
	private static final String JSONDATA = "data";
	
	/**
     * 成功！格式化json返回（data为null）
     * @return 返回json数据，包含状态码、消息、数据，例:{"code":1, "msg":"成功"}
     * @author kwum
     */
    public static String success(){
        JSONObject json = new JSONObject();
        json.put(JSONCODE, 1);
        json.put(JSONMSG, "成功");
        return json.toString();
    }
	
	/**
	 * 成功！格式化json返回（data里有任意值）
	 * @param data 字符串类型的数据
	 * @return 返回json数据，包含状态码、消息、数据，例:{"code":1, "msg":"成功", "data":"看情况而定"}
	 * @author kwum
	 */
	public static String success(Object data){
		JSONObject json = new JSONObject();
		json.put(JSONCODE, 1);
		json.put(JSONMSG, "成功");
		json.put(JSONDATA, data);
		return json.toString();
	}
	
	/**
	 * 错误！格式化json返回（data为null）
	 * @param code 状态码：1成功、-1服务器出错、其余情况请查看返回错误码文档
	 * @return 返回json数据，包含状态码、消息、数据，例:{"code":-1, "msg":"成功", "data":null}
	 * @author kwum
	 */
	public static String error(int code){
		JSONObject json = new JSONObject();
		json.put(JSONCODE, code);
		if(code == 1){
			json.put(JSONMSG, "成功");
			return json.toString();
		}
		if(code == -1){
			json.put(JSONMSG, "服务器繁忙, 请稍后再试");
			return json.toString();
		}
		json.put(JSONMSG, ErrorCodeMap.MAP.get(String.valueOf(code)));
		return json.toString();
	}
	
	/**
     * 消息！格式化json返回（data里有任意值）
     * @param code 状态码：1成功、-1服务器出错、其余情况请查看返回错误码文档
     * @param msg 消息，任意值，放在data当中
     * @return 返回json数据，包含状态码、消息、数据，例:{"code":-1, "msg":"服务器繁忙, 请稍后再试", "data":"视情况而定"}
     * @author kwum
     */
	public static String message(int code, Object msg){
        JSONObject json = new JSONObject();
        json.put(JSONCODE, code);
        json.put(JSONDATA, msg);
        if(code == 1){
            json.put(JSONMSG, "成功");
            return json.toString();
        }
        if(code == -1){
            json.put(JSONMSG, "服务器繁忙, 请稍后再试");
            return json.toString();
        }
        json.put(JSONMSG, ErrorCodeMap.MAP.get(String.valueOf(code)));
        return json.toString();
    }
}
