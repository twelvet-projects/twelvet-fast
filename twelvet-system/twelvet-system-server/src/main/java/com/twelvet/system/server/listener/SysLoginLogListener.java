package com.twelvet.system.server.listener;

import com.twelvet.framework.log.event.SysLoginLogEvent;
import com.twelvet.system.api.domain.SysLoginInfo;
import com.twelvet.system.server.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 异步监听系统登录日志事件
 */
@Component
public class SysLoginLogListener {

    @Autowired
    private ISysLoginInfoService iSysLoginInfoService;

	@Async
	@Order
	@EventListener(SysLoginLogEvent.class)
	public void saveSysLog(SysLoginLogEvent event) {
		SysLoginInfo loginInfo = (SysLoginInfo) event.getSource();
		iSysLoginInfoService.insertLoginInfo(loginInfo);
	}

}
