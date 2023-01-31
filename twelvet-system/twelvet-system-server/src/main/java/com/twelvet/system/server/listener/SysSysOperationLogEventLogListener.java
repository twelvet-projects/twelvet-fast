package com.twelvet.system.server.listener;

import com.twelvet.framework.log.event.SysOperationLogEvent;
import com.twelvet.framework.utils.SpringUtils;
import com.twelvet.system.api.domain.SysLoginInfo;
import com.twelvet.system.api.domain.SysOperationLog;
import com.twelvet.system.server.service.ISysOperationLogService;
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
public class SysSysOperationLogEventLogListener {

	@Autowired
	private ISysOperationLogService iSysOperationLogService;

	@Async
	@Order
	@EventListener(SysOperationLogEvent.class)
	public void saveSysLog(SysOperationLogEvent event) {
		SysOperationLog sysOperationLog = (SysOperationLog) event.getSource();
		iSysOperationLogService.insertOperationLog(sysOperationLog);
	}

}
