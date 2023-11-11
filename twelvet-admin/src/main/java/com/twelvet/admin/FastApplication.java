package com.twelvet.admin;

import com.twelvet.framework.datasource.annotation.EnableDynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 启动入口
 */
@EnableDynamicDataSource
@MapperScan(basePackages = { "com.twelvet.**.mapper" })
@ComponentScan(basePackages = { "com.twelvet" })
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class FastApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastApplication.class, args);
	}

}
