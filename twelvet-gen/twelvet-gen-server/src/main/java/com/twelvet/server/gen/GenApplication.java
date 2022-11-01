package com.twelvet.server.gen;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 启动器
 */
@MapperScan("com.twelvet.**.mapper")
@SpringBootApplication
public class GenApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenApplication.class, args);
	}

}
