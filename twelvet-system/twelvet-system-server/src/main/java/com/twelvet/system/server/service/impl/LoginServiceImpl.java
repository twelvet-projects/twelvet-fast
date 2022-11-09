package com.twelvet.system.server.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.log.event.SysLoginLogEvent;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.enums.DeviceType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.framework.utils.http.IpUtils;
import com.twelvet.system.api.domain.SysLoginInfo;
import com.twelvet.system.api.domain.SysUser;
import com.twelvet.system.api.domain.dto.LoginDTO;
import com.twelvet.system.api.domain.vo.LoginVO;
import com.twelvet.system.server.mapper.SysUserMapper;
import com.twelvet.system.server.service.ISysPermissionService;
import com.twelvet.system.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 登录服务
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 登录
     *
     * @param loginDTO LoginDTO
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        SysUser sysUser = loadUserByUsername(loginDTO.getUsername());

        if (!BCrypt.checkpw(loginDTO.getPassword(), sysUser.getPassword())) {
            throw new TWTException("账号密码不正确");
        }

        // 构建用户信息
        LoginUser loginUser = buildLoginUser(sysUser);

        // 生成token
        SecurityUtils.loginByDevice(loginUser, DeviceType.PC);
        sendLog(loginUser);
        LoginVO loginVO = new LoginVO();

        loginVO.setAccessToken(StpUtil.getTokenValue());

        return loginVO;
    }

    /**
     * 通过账号名称登录
     *
     * @param username 账号名称
     * @return 用户
     */
    private SysUser loadUserByUsername(String username) {
        SysUser sysUser = userMapper.selectUserByUserName(username);
        if (TUtils.isEmpty(username)) {
            throw new TWTException("登录用户：" + username + " 不存在.");
        }
        if (sysUser.getStatus().equals("1")) {
            throw new TWTException("账号已被冻结");
        }
        return sysUser;
    }

    /**
     * 构建用户信息
     *
     * @param sysUser SysUser
     * @return LoginUser
     */
    private LoginUser buildLoginUser(SysUser sysUser) {
        LoginUser loginUser = new LoginUser();

        loginUser.setUserId(sysUser.getUserId());
        loginUser.setUsername(sysUser.getUsername());
        loginUser.setDeptId(sysUser.getDeptId());

        // 角色集合
        Set<String> roles = iSysPermissionService.getRolePermission(sysUser.getUserId());
        // 权限集合
        Set<String> permissions = iSysPermissionService.getMenuPermission(sysUser.getUserId());

        loginUser.setPermissions(permissions);
        loginUser.setRoles(roles);
        return loginUser;
    }

    /**
     * 发送登录成功/失败日志
     */
    private void sendLog(LoginUser userInfo) {
        // 发送异步日志事件
        String username = userInfo.getUsername();
        Long deptId = userInfo.getDeptId();
        SysLoginInfo sysLoginInfo = new SysLoginInfo();
        sysLoginInfo.setStatus(SecurityConstants.LOGIN_SUCCESS);
        sysLoginInfo.setUserName(username);
        sysLoginInfo.setDeptId(deptId);
        sysLoginInfo.setIpaddr(IpUtils.getIpAddr());
        sysLoginInfo.setMsg("登录成功");
        // 发送异步日志事件
        sysLoginInfo.setCreateTime(DateUtils.getNowDate());
        sysLoginInfo.setCreateBy(username);
        sysLoginInfo.setUpdateBy(username);
        SpringContextHolder.publishEvent(new SysLoginLogEvent(sysLoginInfo));
    }

}
