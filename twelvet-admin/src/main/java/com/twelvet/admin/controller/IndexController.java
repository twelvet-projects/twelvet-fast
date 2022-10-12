package com.twelvet.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: index
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
