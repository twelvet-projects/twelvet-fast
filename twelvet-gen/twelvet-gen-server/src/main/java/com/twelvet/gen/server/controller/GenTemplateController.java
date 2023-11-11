package com.twelvet.gen.server.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.jdbc.web.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.gen.api.domain.GenTemplate;
import com.twelvet.gen.server.service.IGenTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码生成业务模板Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Tag(description = "GenTemplateController", name = "代码生成业务模板")
@RestController
@RequestMapping("/gen/template")
public class GenTemplateController extends TWTController {

	@Autowired
	private IGenTemplateService genTemplateService;

	/**
	 * 查询代码生成业务模板分页
	 */
	@Operation(summary = "查询代码生成业务模板分页")
	@SaCheckPermission("gen:template:list")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenTemplate>> pageQuery(GenTemplate genTemplate) {
		PageUtils.startPage();
		List<GenTemplate> list = genTemplateService.selectGenTemplateList(genTemplate);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 获取代码生成业务模板详细信息
	 */
	@Operation(summary = "获取代码生成业务模板详细信息")
	@SaCheckPermission("gen:template:query")
	@GetMapping(value = "/{id}")
	public JsonResult<GenTemplate> getInfo(@PathVariable("id") Long id) {
		return JsonResult.success(genTemplateService.selectGenTemplateById(id));
	}

	/**
	 * 新增代码生成业务模板
	 */
	@Operation(summary = "新增代码生成业务模板")
	@SaCheckPermission("gen:template:add")
	@Log(service = "代码生成业务模板", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenTemplate genTemplate) {
		return json(genTemplateService.insertGenTemplate(genTemplate));
	}

	/**
	 * 修改代码生成业务模板
	 */
	@Operation(summary = "修改代码生成业务模板")
	@SaCheckPermission("gen:template:edit")
	@Log(service = "代码生成业务模板", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenTemplate genTemplate) {
		return json(genTemplateService.updateGenTemplate(genTemplate));
	}

	/**
	 * 删除代码生成业务模板
	 */
	@Operation(summary = "删除代码生成业务模板")
	@SaCheckPermission("gen:template:remove")
	@Log(service = "代码生成业务模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public JsonResult<String> remove(@PathVariable Long[] ids) {
		return json(genTemplateService.deleteGenTemplateByIds(ids));
	}

}
