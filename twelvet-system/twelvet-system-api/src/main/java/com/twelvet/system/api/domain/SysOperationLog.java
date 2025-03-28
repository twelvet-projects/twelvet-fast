package com.twelvet.system.api.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.Arrays;
import java.util.Date;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 操作日志记录表
 */
@Schema(description = "操作日志记录表")
public class SysOperationLog extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 日志主键
	 */
	@Schema(description = "日志主键")
	@ExcelProperty(value = "操作序号")
	private Long operId;

	/**
	 * 操作模块
	 */
	@Schema(description = "操作模块")
	@ExcelProperty(value = "操作模块")
	private String service;

	/**
	 * 业务类型（0其它 1新增 2修改 3删除）
	 */
	@Schema(description = "业务类型")
	@ExcelProperty(value = "业务类型(0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据)")
	private Integer businessType;

	/**
	 * 业务类型数组
	 */
	@Schema(description = "业务类型数组")
	private Integer[] businessTypes;

	/**
	 * 请求方法
	 */
	@Schema(description = "请求方法")
	@ExcelProperty(value = "请求方法")
	private String method;

	/**
	 * 请求方式
	 */
	@Schema(description = "请求方式")
	@ExcelProperty(value = "请求方式")
	private String requestMethod;

	/**
	 * 操作类别（0其它 1后台用户 2手机端用户）
	 */
	@Schema(description = "操作类别")
	@ExcelProperty(value = "操作类别(0=其它,1=后台用户,2=手机端用户)")
	private Integer operatorType;

	/**
	 * 操作人员
	 */
	@Schema(description = "操作人员")
	@ExcelProperty(value = "操作人员")
	private String operName;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	@ExcelProperty(value = "部门名称")
	private String deptName;

	/**
	 * 请求url
	 */
	@Schema(description = "请求url")
	@ExcelProperty(value = "请求地址")
	private String operUrl;

	/**
	 * 操作地址
	 */
	@Schema(description = "操作地址")
	@ExcelProperty(value = "操作地址")
	private String operIp;

	/**
	 * 请求参数
	 */
	@Schema(description = "请求参数")
	@ExcelProperty(value = "请求参数")
	private String operParam;

	/**
	 * 返回参数
	 */
	@Schema(description = "返回参数")
	@ExcelProperty(value = "返回参数")
	private String jsonResult;

	/**
	 * 操作状态（0正常 1异常）
	 */
	@Schema(description = "操作状态")
	@ExcelProperty(value = "状态(0=正常,1=异常)")
	private Integer status;

	/**
	 * 错误消息
	 */
	@Schema(description = "错误消息")
	@ExcelProperty(value = "错误消息")
	private String errorMsg;

	/**
	 * 操作时间
	 */
	@Schema(description = "操作时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty(value = "操作时间")
	private Date operTime;

	/**
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	@ExcelProperty(value = "部门ID")
	private Long deptId;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer[] getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(Integer[] businessTypes) {
		this.businessTypes = businessTypes;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOperUrl() {
		return operUrl;
	}

	public void setOperUrl(String operUrl) {
		this.operUrl = operUrl;
	}

	public String getOperIp() {
		return operIp;
	}

	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

	public String getOperParam() {
		return operParam;
	}

	public void setOperParam(String operParam) {
		this.operParam = operParam;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	@Override
	public String toString() {
		return "SysOperationLog{" + "operId=" + operId + ", service='" + service + '\'' + ", businessType="
				+ businessType + ", businessTypes=" + Arrays.toString(businessTypes) + ", method='" + method + '\''
				+ ", requestMethod='" + requestMethod + '\'' + ", operatorType=" + operatorType + ", operName='"
				+ operName + '\'' + ", deptName='" + deptName + '\'' + ", operUrl='" + operUrl + '\'' + ", operIp='"
				+ operIp + '\'' + ", operParam='" + operParam + '\'' + ", jsonResult='" + jsonResult + '\''
				+ ", status=" + status + ", errorMsg='" + errorMsg + '\'' + ", operTime=" + operTime + ", deptId='"
				+ deptId + '\'' + '}';
	}

}
