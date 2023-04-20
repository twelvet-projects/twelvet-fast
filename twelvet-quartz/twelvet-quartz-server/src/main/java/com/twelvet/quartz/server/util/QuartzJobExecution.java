package com.twelvet.quartz.server.util;

import com.twelvet.quartz.api.domain.SysJob;
import org.quartz.JobExecutionContext;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 定时任务工具类
 */
public class QuartzJobExecution extends AbstractQuartzJob {

	@Override
	protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
		JobInvokeUtil.invokeMethod(sysJob);
	}

}
