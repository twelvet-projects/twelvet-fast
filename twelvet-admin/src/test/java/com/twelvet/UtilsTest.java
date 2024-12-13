package com.twelvet;

import cn.dev33.satoken.secure.BCrypt;
import com.twelvet.admin.FastApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 工具测试
 */
@SpringBootTest(classes = FastApplication.class)
public class UtilsTest {

	private final static Logger log = LoggerFactory.getLogger(UtilsTest.class);

	@Test
	public void utilTest() {

		log.info("params: {}", "${jndi:ldap://127.0.0.1:1389/Log4jTest}");
	}

}
