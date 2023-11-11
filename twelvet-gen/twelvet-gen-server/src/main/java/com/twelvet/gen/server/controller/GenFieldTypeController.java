package com.twelvet.gen.server.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.jdbc.web.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.gen.api.domain.GenFieldType;
import com.twelvet.gen.server.service.IGenFieldTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字段类型管理Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Tag(description = "GenFieldTypeController", name = "字段类型管理")
@RestController
@RequestMapping("/gen/type")
public class GenFieldTypeController extends TWTController {

	@Autowired
	private IGenFieldTypeService genFieldTypeService;

	/**
	 * 查询字段类型管理列表
	 */
	@Operation(summary = "查询字段类型管理分页")
	@SaCheckPermission("gen:FieldType:list")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenFieldType>> pageQuery(GenFieldType genFieldType) {
		PageUtils.startPage();
		List<GenFieldType> list = genFieldTypeService.selectGenFieldTypeList(genFieldType);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 获取字段类型管理详细信息
	 */
	@Operation(summary = "获取字段类型管理详细信息")
	@SaCheckPermission("gen:FieldType:query")
	@GetMapping(value = "/{id}")
	public JsonResult<GenFieldType> getInfo(@PathVariable("id") Long id) {
		return JsonResult.success(genFieldTypeService.selectGenFieldTypeById(id));
	}

	/**
	 * 新增字段类型管理
	 */
	@Operation(summary = "新增字段类型管理")
	@SaCheckPermission("gen:FieldType:add")
	@Log(service = "字段类型管理", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenFieldType genFieldType) {
		return json(genFieldTypeService.insertGenFieldType(genFieldType));
	}

	/**
	 * 修改字段类型管理
	 */
	@Operation(summary = "修改字段类型管理")
	@SaCheckPermission("gen:FieldType:edit")
	@Log(service = "字段类型管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenFieldType genFieldType) {
		return json(genFieldTypeService.updateGenFieldType(genFieldType));
	}

	/**
	 * 删除字段类型管理
	 */
	@Operation(summary = "删除字段类型管理")
	@SaCheckPermission("gen:FieldType:remove")
	@Log(service = "字段类型管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public JsonResult<String> remove(@PathVariable Long[] ids) {
		return json(genFieldTypeService.deleteGenFieldTypeByIds(ids));
	}

}
