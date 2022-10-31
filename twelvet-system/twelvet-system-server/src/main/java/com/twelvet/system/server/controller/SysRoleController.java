package com.twelvet.system.server.controller;

import com.twelvet.system.api.domain.SysRole;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.jdbc.web.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.system.server.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 角色信息
 */
@Tag(description = "SysRoleController", name = "角色信息")
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends TWTController {

	@Autowired
	private ISysRoleService iSysRoleService;

	/**
	 * 角色信息分页查询
	 * @param role SysRole
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "角色信息分页查询")
	@GetMapping("/pageQuery")
	@SaCheckPermission("system:role:list")
	public JsonResult<TableDataInfo> pageQuery(SysRole role) {
		PageUtils.startPage();
		List<SysRole> list = iSysRoleService.selectRoleList(role);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 数据导出
	 * @param response HttpServletResponse
	 * @param role SysRole
	 */
	@Operation(summary = "数据导出")
	@Log(service = "角色管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@SaCheckPermission("system:role:export")
	public void export(HttpServletResponse response, @RequestBody SysRole role) {
		List<SysRole> list = iSysRoleService.selectRoleList(role);
		ExcelUtils<SysRole> excelUtils = new ExcelUtils<>(SysRole.class);
		excelUtils.exportExcel(response, list, "角色数据");
	}

	/**
	 * 根据角色编号获取详细信息
	 * @param roleId 角色ID
	 * @return JsonResult<SysRole>
	 */
	@Operation(summary = "根据角色编号获取详细信息")
	@GetMapping("/{roleId}")
	@SaCheckPermission("system:role:query")
	public JsonResult<SysRole> getInfo(@PathVariable Long roleId) {
		iSysRoleService.checkRoleDataScope(roleId);
		return JsonResult.success(iSysRoleService.selectRoleById(roleId));
	}

	/**
	 * 新增角色
	 * @param role SysRole
	 * @return JsonResult<String>
	 */
	@Operation(summary = "新增角色")
	@Log(service = "角色管理", businessType = BusinessType.INSERT)
	@PostMapping
	@SaCheckPermission("system:role:insert")
	public JsonResult<String> insert(@Validated @RequestBody SysRole role) {
		if (UserConstants.NOT_UNIQUE.equals(iSysRoleService.checkRoleNameUnique(role))) {
			return JsonResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
		}
		else if (UserConstants.NOT_UNIQUE.equals(iSysRoleService.checkRoleKeyUnique(role))) {
			return JsonResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setCreateBy(SecurityUtils.getUsername());
		return json(iSysRoleService.insertRole(role));
	}

	/**
	 * 修改保存角色
	 * @param role SysRole
	 * @return JsonResult<String>
	 */
	@Operation(summary = "修改保存角色")
	@Log(service = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@SaCheckPermission("system:role:update")
	public JsonResult<String> update(@Validated @RequestBody SysRole role) {
		iSysRoleService.checkRoleAllowed(role);
		iSysRoleService.checkRoleDataScope(role.getRoleId());
		if (UserConstants.NOT_UNIQUE.equals(iSysRoleService.checkRoleNameUnique(role))) {
			return JsonResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
		}
		else if (UserConstants.NOT_UNIQUE.equals(iSysRoleService.checkRoleKeyUnique(role))) {
			return JsonResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setUpdateBy(SecurityUtils.getUsername());
		return json(iSysRoleService.updateRole(role));
	}

	/**
	 * 状态修改
	 * @param role SysRole
	 * @return JsonResult<String>
	 */
	@Operation(summary = "状态修改")
	@Log(service = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	@SaCheckPermission("system:role:update")
	public JsonResult<String> changeStatus(@RequestBody SysRole role) {
		iSysRoleService.checkRoleAllowed(role);
		role.setUpdateBy(SecurityUtils.getUsername());
		return json(iSysRoleService.updateRoleStatus(role));
	}

	/**
	 * 删除角色
	 */
	@Operation(summary = "删除角色")
	@Log(service = "角色管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roleIds}")
	@SaCheckPermission("system:role:remove")
	public JsonResult<String> remove(@PathVariable Long[] roleIds) {
		return json(iSysRoleService.deleteRoleByIds(roleIds));
	}

	/**
	 * 获取角色选择框列表
	 */
	@Operation(summary = "获取角色选择框列表")
	@GetMapping("/optionSelect")
	@SaCheckPermission("system:role:query")
	public JsonResult<List<SysRole>> optionSelect() {
		return JsonResult.success(iSysRoleService.selectRoleAll());
	}

}
