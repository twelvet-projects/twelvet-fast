package com.twelvet.system.api.domain.vo;

import com.twelvet.system.api.domain.SysUser;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 用户信息
 */
public class UserInfoVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户信息
	 */
	private SysUser user;

	/**
	 * 权限组
	 */
	private String roleGroup;

	/**
	 * 角色岗位
	 */
	private String postGroup;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	public String getPostGroup() {
		return postGroup;
	}

	public void setPostGroup(String postGroup) {
		this.postGroup = postGroup;
	}

	@Override
	public String toString() {
		return "UserInfoVo{" + "user=" + user + ", roleGroup='" + roleGroup + '\'' + ", postGroup='" + postGroup + '\''
				+ '}';
	}

}
