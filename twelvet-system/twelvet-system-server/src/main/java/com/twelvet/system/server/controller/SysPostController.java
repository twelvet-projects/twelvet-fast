package com.twelvet.system.server.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.jdbc.web.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.system.api.domain.SysPost;
import com.twelvet.system.server.service.ISysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 岗位信息操作处理
 */
@Tag(description = "SysPostController", name = "岗位信息操作处理")
@RestController
@RequestMapping("/system/post")
public class SysPostController extends TWTController {

	@Autowired
	private ISysPostService iSysPostService;

	/**
	 * 新增岗位
	 * @param sysPost SysPost
	 * @return JsonResult<String>
	 */
	@Operation(summary = "新增岗位")
	@Log(service = "岗位管理", businessType = BusinessType.INSERT)
	@PostMapping
	@SaCheckPermission("system:post:insert")
	public JsonResult<String> insert(@Validated @RequestBody SysPost sysPost) {
		if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostNameUnique(sysPost))) {
			return JsonResult.error("新增岗位'" + sysPost.getPostName() + "'失败，岗位名称已存在");
		}
		else if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostCodeUnique(sysPost))) {
			return JsonResult.error("新增岗位'" + sysPost.getPostName() + "'失败，岗位编码已存在");
		}
		sysPost.setCreateBy(SecurityUtils.getUsername());
		return json(iSysPostService.insertPost(sysPost));
	}

	/**
	 * 删除岗位
	 * @param postIds 唯一ID数组
	 * @return JsonResult<String>
	 */
	@Operation(summary = "删除岗位")
	@Log(service = "岗位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
	@SaCheckPermission("system:post:remove")
	public JsonResult<String> remove(@PathVariable Long[] postIds) {
		return json(iSysPostService.deletePostByIds(postIds));
	}

	/**
	 * 修改岗位
	 * @param sysPost SysPost
	 * @return JsonResult<String>
	 */
	@Operation(summary = "修改岗位")
	@Log(service = "岗位管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@SaCheckPermission("system:post:update")
	public JsonResult<String> update(@Validated @RequestBody SysPost sysPost) {
		if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostNameUnique(sysPost))) {
			return JsonResult.error("修改岗位'" + sysPost.getPostName() + "'失败，岗位名称已存在");
		}
		else if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostCodeUnique(sysPost))) {
			return JsonResult.error("修改岗位'" + sysPost.getPostName() + "'失败，岗位编码已存在");
		}
		sysPost.setUpdateBy(SecurityUtils.getUsername());
		return json(iSysPostService.updatePost(sysPost));
	}

	/**
	 * 获取岗位列表
	 * @param post SysPost
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "获取岗位列表")
	@GetMapping("/pageQuery")
	@SaCheckPermission("system:post:list")
	public JsonResult<TableDataInfo<SysPost>> pageQuery(SysPost post) {
		PageUtils.startPage();
		List<SysPost> list = iSysPostService.selectPostList(post);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 根据岗位编号获取详细信息
	 * @param postId 唯一ID
	 * @return JsonResult<SysPost>
	 */
	@Operation(summary = "根据岗位编号获取详细信息")
	@GetMapping("/{postId}")
	@SaCheckPermission("system:post:query")
	public JsonResult<SysPost> getByPostId(@PathVariable Long postId) {
		return JsonResult.success(iSysPostService.selectPostById(postId));
	}

	/**
	 * 获取岗位选择框列表
	 * @return JsonResult<List<SysPost>>
	 */
	@Operation(summary = "获取岗位选择框列表")
	@GetMapping("/optionSelect")
	public JsonResult<List<SysPost>> optionSelect() {
		List<SysPost> posts = iSysPostService.selectPostAll();
		return JsonResult.success(posts);
	}

	/**
	 * 岗位管理数据导出
	 * @param sysPost SysPost
	 * @return List<SysPost>
	 */
	@ResponseExcel(name = "岗位管理")
	@Operation(summary = "岗位管理数据导出")
	@Log(service = "岗位管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@SaCheckPermission("system:post:export")
	public List<SysPost> export(@RequestBody SysPost sysPost) {
		return iSysPostService.selectPostList(sysPost);
	}

}
