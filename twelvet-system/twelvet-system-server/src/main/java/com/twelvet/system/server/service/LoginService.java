package com.twelvet.system.server.service;

import com.twelvet.system.api.domain.dto.LoginDTO;
import com.twelvet.system.api.domain.vo.LoginVO;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 登录服务
 */
public interface LoginService {

	/**
	 * 登录
	 * @param loginDTO LoginDTO
	 */
	LoginVO login(LoginDTO loginDTO);

	/**
	 * 退出登录
	 */
	void logout();

}
