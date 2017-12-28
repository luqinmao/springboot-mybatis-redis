package com.youjiang.springboot.constant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Description: 用户状态
 * Company: youjiang
 * @author Kwum
 * @date 2017年10月27日 上午11:31:36
 * @version 1.0
 */
public enum UserState {
	normal(1, "正常"),
	frozen(0, "冻结")
	;
	
	private int value;
	private String name;
	
	UserState(int value, String name) {
		this.value = value;
		this.name= name;
	}

	public static UserState getByValue(int value) {
		for(UserState ordersState : values()) {
			if(ordersState.getValue() == value) {
				return ordersState;
			}
		}
		return null;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static  UserState  getByName(String name) {
		for(UserState ordersState : values()) {
			if(ordersState.getName().equals(name)) {
				return ordersState;
			}
		}
		return null;
	}
	
	public static JSONArray getList(){
        JSONArray array = new JSONArray();
        for(UserState state : values()) {
            JSONObject json = new JSONObject();
            json.put("name", state.getName());
            json.put("value", state.getValue());
            array.add(json);
        }
        return array;
    }
}
