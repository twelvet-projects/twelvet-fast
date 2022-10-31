package com.twelvet.framework.security.service;

import cn.dev33.satoken.stp.StpInterface;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import org.apache.xmlbeans.UserType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
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
        return new ArrayList<>(loginUser.getRoles());
    }
}