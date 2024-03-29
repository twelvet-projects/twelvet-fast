package com.twelvet.gen.server.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.twelvet.framework.jdbc.web.page.TableDataInfo;
import com.twelvet.gen.api.domain.GenGroup;
import com.twelvet.gen.api.domain.GenTemplate;
import com.twelvet.gen.api.domain.dto.GenGroupDTO;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.gen.server.service.IGenGroupService;
import com.twelvet.gen.server.service.IGenTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模板分组Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Tag(description = "GenGroupController", name = "模板分组")
@RestController
@RequestMapping("/gen/templateGroup")
public class GenGroupController extends TWTController {

	@Autowired
	private IGenGroupService genGroupService;

	/**
	 * 查询代码生成业务所有模板列表
	 */
	@Operation(summary = "查询代码生成业务所有模板列表")
	@SaCheckPermission("gen:group:list")
	@GetMapping("/queryTemplateList")
	public JsonResult<List<GenTemplate>> selectGenTemplateAll() {
		return JsonResult.success(genGroupService.selectGenTemplateAll());
	}

	/**
	 * 查询模板分组列表
	 */
	@Operation(summary = "查询模板分组分页")
	@SaCheckPermission("gen:group:list")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenGroup>> pageQuery(GenGroup genGroup) {
		PageUtils.startPage();
		List<GenGroup> list = genGroupService.selectGenGroupList(genGroup);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 获取模板分组详细信息
	 */
	@Operation(summary = "获取模板分组详细信息")
	@SaCheckPermission("gen:group:query")
	@GetMapping(value = "/{id}")
	public JsonResult<GenGroupDTO> getInfo(@PathVariable("id") Long id) {
		return JsonResult.success(genGroupService.selectGenGroupById(id));
	}

	/**
	 * 新增模板分组
	 */
	@Operation(summary = "新增模板分组")
	@SaCheckPermission("gen:group:add")
	@Log(service = "模板分组", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenGroupDTO genGroupDTO) {
		return json(genGroupService.insertGenGroup(genGroupDTO));
	}

	/**
	 * 修改模板分组
	 */
	@Operation(summary = "修改模板分组")
	@SaCheckPermission("gen:group:edit")
	@Log(service = "模板分组", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenGroupDTO genGroupDTO) {
		return json(genGroupService.updateGenGroup(genGroupDTO));
	}

	/**
	 * 删除模板分组
	 */
	@Operation(summary = "删除模板分组")
	@SaCheckPermission("gen:group:remove")
	@Log(service = "模板分组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public JsonResult<String> remove(@PathVariable Long[] ids) {
		return json(genGroupService.deleteGenGroupByIds(ids));
	}

}
