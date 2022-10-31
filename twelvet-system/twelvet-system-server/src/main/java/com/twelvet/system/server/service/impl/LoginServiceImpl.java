package com.twelvet.system.server.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.twelvet.framework.utils.http.ServletUtils;
import com.twelvet.system.api.domain.SysUser;
import com.twelvet.system.api.domain.dto.LoginDTO;
import com.twelvet.system.api.domain.vo.LoginVO;
import com.twelvet.system.server.mapper.SysUserMapper;
import com.twelvet.system.server.service.ISysUserService;
import com.twelvet.system.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 登录服务
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 登录
     *
     * @param loginDTO LoginDTO
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        Optional<HttpServletRequest> request = ServletUtils.getRequest();
        SysUser user = loadUserByUsername(loginDTO.getUsername());
        StpUtil.login(1);
        LoginVO loginVO = new LoginVO();

        loginVO.setAccessToken(StpUtil.getTokenValue());

        return loginVO;
    }




    private SysUser loadUserByUsername(String username) {
        SysUser user = selectUserByUserName(username);
        if(ObjectUtil.isNull(username)){
            System.out.println("登录用户："+username+" 不存在.");
        } else {
            System.out.println("登录用户："+username+" 已被删除.");

        }
        return user;
    }




    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    public SysUser selectUserByUserName(String username) {
        return userMapper.selectUserByUserName(username);
    }

}
