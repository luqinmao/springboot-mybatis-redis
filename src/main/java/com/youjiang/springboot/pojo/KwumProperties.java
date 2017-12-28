package com.youjiang.springboot.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: 自定义配置参数模型类
 * Company: youjiang
 * @author Kwum
 * @date 2017年4月20日 上午10:16:49
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "kwum")
public class KwumProperties {

	private String look;
	
	private String height;
	
	public String getLook() {
		return look;
	}

	public void setLook(String look) {
		this.look = look;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
