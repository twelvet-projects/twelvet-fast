package com.twelvet.system.server.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.twelvet.system.api.domain.dto.LoginDTO;
import com.twelvet.system.api.domain.vo.LoginVO;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 登录服务
 */
@Service
public class LoginServiceImpl implements LoginService {

    /**
     * 登录
     *
     * @param loginDTO LoginDTO
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        StpUtil.login(1);
        LoginVO loginVO = new LoginVO();

        loginVO.setAccessToken(StpUtil.getTokenValue());

        return loginVO;
    }
}
