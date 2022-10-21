package com.twelvet.admin.controller;

import com.twelvet.framework.core.config.properties.TWProperties;
import com.twelvet.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 首页
 */
@RestController
public class IndexController {

    @Autowired
    private TWProperties twProperties;

    @GetMapping("/")
    public String index() {
        return StringUtils.format("<h1 style=\"text-align: center\">欢迎使用 {} 后台管理框架，当前版本：v{}。</h1>", twProperties.getName(), twProperties.getVersion());
    }

}
