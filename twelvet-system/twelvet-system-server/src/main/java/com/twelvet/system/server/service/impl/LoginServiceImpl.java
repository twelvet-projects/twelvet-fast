package com.twelvet.system.server.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.twelvet.framework.security.constants.SecurityConstants;
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

		LoginVO loginVO = new LoginVO();

		loginVO.setAccessToken(StpUtil.getTokenValue());

		return loginVO;
	}

	/**
	 * 退出登录
	 */
	@Override
	public void logout() {
		Long deptId = SecurityUtils.getDeptId();
		String username = SecurityUtils.getUsername();

		// 发送异步日志事件
		SysLoginInfo sysLoginInfo = new SysLoginInfo();
		sysLoginInfo.setStatus(SecurityConstants.LOGIN_SUCCESS);
		sysLoginInfo.setUserName(username);
		sysLoginInfo.setDeptId(deptId);
		sysLoginInfo.setIpaddr(IpUtils.getIpAddr());
		sysLoginInfo.setMsg("退出登录");
		// 发送异步日志事件
		sysLoginInfo.setCreateTime(DateUtils.getNowDate());
		sysLoginInfo.setCreateBy(username);
		sysLoginInfo.setUpdateBy(username);
		SpringContextHolder.publishEvent(new SysLoginLogEvent(sysLoginInfo));

		StpUtil.logout();
	}

	/**
	 * 通过账号名称登录
	 * @param username 账号名称
	 * @return 用户
	 */
	private SysUser loadUserByUsername(String username) {
		SysUser sysUser = userMapper.selectUserByUserName(username);
		if (TUtils.isEmpty(username)) {
			throw new TWTException("登录用户：" + username + " 不存在.");
		}

		// 发送异步日志事件
		Long deptId = sysUser.getDeptId();
		SysLoginInfo sysLoginInfo = new SysLoginInfo();
		sysLoginInfo.setStatus(SecurityConstants.LOGIN_SUCCESS);
		sysLoginInfo.setUserName(username);
		sysLoginInfo.setDeptId(deptId);
		sysLoginInfo.setIpaddr(IpUtils.getIpAddr());
		sysLoginInfo.setMsg("登录成功");

		if (sysUser.getStatus().equals("1")) {
			String error = "账号已被冻结";
			sysLoginInfo.setMsg(error);
			sysLoginInfo.setStatus(SecurityConstants.LOGIN_FAIL);
			// 发送异步日志事件
			sysLoginInfo.setCreateTime(DateUtils.getNowDate());
			sysLoginInfo.setCreateBy(username);
			sysLoginInfo.setUpdateBy(username);
			SpringContextHolder.publishEvent(new SysLoginLogEvent(sysLoginInfo));
			throw new TWTException(error);
		}

		// 发送异步日志事件
		sysLoginInfo.setCreateTime(DateUtils.getNowDate());
		sysLoginInfo.setCreateBy(username);
		sysLoginInfo.setUpdateBy(username);
		SpringContextHolder.publishEvent(new SysLoginLogEvent(sysLoginInfo));
		return sysUser;
	}

	/**
	 * 构建用户信息
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

}
