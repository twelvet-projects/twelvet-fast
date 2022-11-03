package com.twelvet.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.system.api.domain.dto.LoginDTO;
import com.twelvet.system.api.domain.vo.LoginVO;
import com.twelvet.system.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 登录控制器
 */
@RestController
@RequestMapping("/auth/oauth2")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     *
     * @param loginDTO 用户登录信息
     * @return JsonResult<String>
     */
    @SaIgnore
    @PostMapping("/token")
    public Map<String, Object> login(@Validated @RequestBody LoginDTO loginDTO) {
        LoginVO login = loginService.login(loginDTO);
        Map<String, Object> res = new HashMap<>();
        res.put("access_token", login.getAccessToken());
        return res;
    }

    /**
     * 退出登录
     *
     * @return JsonResult<String>
     */
    @PostMapping("/logout")
    public JsonResult<String> logout() {
        StpUtil.logout();
        return JsonResult.success();
    }

}
