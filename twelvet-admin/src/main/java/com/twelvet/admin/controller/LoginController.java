package com.twelvet.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.system.api.domain.dto.LoginDTO;
import com.twelvet.system.api.domain.vo.LoginVO;
import com.twelvet.system.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 登录控制器
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * 用户登录
	 * @param loginDTO 用户登录信息
	 * @return JsonResult<String>
	 */
	@SaIgnore
	@PostMapping("/oauth2/token")
	public LoginVO login(@Validated @RequestBody LoginDTO loginDTO) {
		return loginService.login(loginDTO);
	}

	/**
	 * 退出登录
	 * @return JsonResult<String>
	 */
	@SaIgnore
	@DeleteMapping("/token/logout")
	public JsonResult<String> logout() {
		loginService.logout();
		return JsonResult.success();
	}

}
