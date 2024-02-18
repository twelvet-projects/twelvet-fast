package com.twelvet.framework.security.service;

import cn.dev33.satoken.stp.StpInterface;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.system.api.domain.SysRole;
import org.apache.xmlbeans.UserType;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: sa-token 权限管理实现类
 */
@Component
public class SaPermissionImpl implements StpInterface {

	/**
	 * 获取菜单权限列表
	 */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		return new ArrayList<>(loginUser.getPermissions());
	}

	/**
	 * 获取角色权限列表
	 */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		List<SysRole> roles = loginUser.getRoles();
		List<String> permsSet = new ArrayList<>();
		for (SysRole role : roles) {
			if (TUtils.isNotEmpty(role)) {
				permsSet.addAll(Arrays.asList(role.getRoleKey().trim().split(",")));
			}
		}
		return permsSet;
	}

}
