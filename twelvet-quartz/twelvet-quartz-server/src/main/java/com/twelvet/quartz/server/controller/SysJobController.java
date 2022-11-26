package com.twelvet.quartz.server.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.constants.Constants;
import com.twelvet.framework.jdbc.web.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.quartz.api.domain.SysJob;
import com.twelvet.quartz.server.exception.TaskException;
import com.twelvet.quartz.server.service.ISysJobService;
import com.twelvet.quartz.server.util.CronUtils;
import com.twelvet.quartz.server.util.ScheduleUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 调度任务信息操作处理
 */
@Tag(description = "SysJobController", name = "调度任务信息操作处理")
@RestController
@RequestMapping("/job/cron")
public class SysJobController extends TWTController {

	@Autowired
	private ISysJobService jobService;

	/**
	 * 查询定时任务列表
	 * @param sysJob SysJob
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "查询定时任务列表")
	@GetMapping("/pageQuery")
	@SaCheckPermission("monitor:job:list")
	public JsonResult<TableDataInfo> pageQuery(SysJob sysJob) {
		PageUtils.startPage();
		List<SysJob> list = jobService.selectJobList(sysJob);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 导出定时任务列表
	 * @param response HttpServletResponse
	 * @param sysJob SysJob
	 */
	@Operation(summary = "导出定时任务列表")
	@Log(service = "定时任务", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@SaCheckPermission("monitor:job:export")
	public void export(HttpServletResponse response, @RequestBody SysJob sysJob) {
		List<SysJob> list = jobService.selectJobList(sysJob);
		ExcelUtils<SysJob> excelUtils = new ExcelUtils<>(SysJob.class);
		excelUtils.exportExcel(response, list, "定时任务");
	}

	/**
	 * 获取定时任务详细信息
	 * @param jobId 定时任务ID
	 * @return JsonResult<SysJob>
	 */
	@Operation(summary = "获取定时任务详细信息")
	@GetMapping(value = "/{jobId}")
	@SaCheckPermission("monitor:job:query")
	public JsonResult<SysJob> getByJobId(@PathVariable("jobId") Long jobId) {
		return JsonResult.success(jobService.selectJobById(jobId));
	}

	/**
	 * 新增定时任务
	 * @param sysJob SysJob
	 * @return JsonResult<String>
	 * @throws SchedulerException 表达式异常
	 * @throws TaskException 任务异常
	 */
	@Operation(summary = "新增定时任务")
	@Log(service = "定时任务", businessType = BusinessType.INSERT)
	@PostMapping
	@SaCheckPermission("monitor:job:insert")
	public JsonResult<String> insert(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(sysJob.getCronExpression())) {
			return error("新增任务'" + sysJob.getJobName() + "'失败，Cron表达式不正确");
		}
		else if (StringUtils.containsIgnoreCase(sysJob.getInvokeTarget(), Constants.LOOKUP_RMI)) {
			return error("新增任务'" + sysJob.getJobName() + "'失败，目标字符串不允许'rmi'调用");
		}
		else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(),
				new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS })) {
			return error("新增任务'" + sysJob.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
		}
		else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(),
				new String[] { Constants.HTTP, Constants.HTTPS })) {
			return error("新增任务'" + sysJob.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
		}
		else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
			return error("新增任务'" + sysJob.getJobName() + "'失败，目标字符串存在违规");
		}
		else if (!ScheduleUtils.whiteList(sysJob.getInvokeTarget())) {
			return error("新增任务'" + sysJob.getJobName() + "'失败，目标字符串不在白名单内");
		}
		sysJob.setCreateBy(SecurityUtils.getUsername());
		sysJob.setCreateBy(SecurityUtils.getUsername());
		return json(jobService.insertJob(sysJob));
	}

	/**
	 * 修改定时任务
	 * @param sysJob SysJob
	 * @return JsonResult<String>
	 * @throws SchedulerException 表达式异常
	 * @throws TaskException 任务异常
	 */
	@Operation(summary = "修改定时任务")
	@Log(service = "定时任务", businessType = BusinessType.UPDATE)
	@PutMapping
	@SaCheckPermission("monitor:job:update")
	public JsonResult<String> update(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(sysJob.getCronExpression())) {
			return error("修改任务'" + sysJob.getJobName() + "'失败，Cron表达式不正确");
		}
		else if (StringUtils.containsIgnoreCase(sysJob.getInvokeTarget(), Constants.LOOKUP_RMI)) {
			return error("修改任务'" + sysJob.getJobName() + "'失败，目标字符串不允许'rmi'调用");
		}
		else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(),
				new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS })) {
			return error("修改任务'" + sysJob.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
		}
		else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(),
				new String[] { Constants.HTTP, Constants.HTTPS })) {
			return error("修改任务'" + sysJob.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
		}
		else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
			return error("修改任务'" + sysJob.getJobName() + "'失败，目标字符串存在违规");
		}
		else if (!ScheduleUtils.whiteList(sysJob.getInvokeTarget())) {
			return error("修改任务'" + sysJob.getJobName() + "'失败，目标字符串不在白名单内");
		}
		sysJob.setUpdateBy(SecurityUtils.getUsername());
		return json(jobService.updateJob(sysJob));
	}

	/**
	 * 定时任务状态修改
	 * @param job SysJob
	 * @return JsonResult<String>
	 * @throws SchedulerException 表达式异常
	 */
	@Operation(summary = "定时任务状态修改")
	@Log(service = "定时任务", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	@SaCheckPermission("monitor:job:update")
	public JsonResult<String> changeStatus(@RequestBody SysJob job) throws SchedulerException {
		SysJob newJob = jobService.selectJobById(job.getJobId());
		newJob.setStatus(job.getStatus());
		return json(jobService.changeStatus(newJob));
	}

	/**
	 * 定时任务立即执行一次
	 * @param job SysJob
	 * @return JsonResult<String>
	 * @throws SchedulerException 表达式异常
	 */
	@Operation(summary = "定时任务立即执行一次")
	@Log(service = "定时任务", businessType = BusinessType.UPDATE)
	@PutMapping("/run")
	public JsonResult<String> run(@RequestBody SysJob job) throws SchedulerException {
		jobService.run(job);
		return JsonResult.success();
	}

	/**
	 * 删除定时任务
	 * @param jobIds 定时任务id数组
	 * @return JsonResult<String>
	 * @throws SchedulerException 表达式异常
	 */
	@Operation(summary = "删除定时任务")
	@Log(service = "定时任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{jobIds}")
	@SaCheckPermission("monitor:job:remove")
	public JsonResult<String> remove(@PathVariable Long[] jobIds) throws SchedulerException {
		jobService.deleteJobByIds(jobIds);
		return JsonResult.success();
	}

}
