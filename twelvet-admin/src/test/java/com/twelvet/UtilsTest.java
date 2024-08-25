package com.twelvet;

import cn.dev33.satoken.secure.BCrypt;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 工具测试
 */
public class UtilsTest {

	public static void main(String[] args) {
		// 密码测试
		System.out.println(BCrypt.hashpw("123456"));
	}

}
