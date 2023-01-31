package com.twelvet.system.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 登录信息DTO
 */
@Schema(description = "登录信息DTO")
public class LoginDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 账号
	 */
	@Schema(description = "账号")
	@NotBlank(message = "用户名不能为空")
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	@NotBlank(message = "密码不能为空")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDTO{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
	}

}
