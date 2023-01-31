package com.twelvet.system.server.controller;

import cn.dev33.satoken.secure.BCrypt;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.system.api.domain.SysUser;
import com.twelvet.system.api.domain.params.UserPassword;
import com.twelvet.system.api.domain.vo.UserInfoVo;
import com.twelvet.system.server.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: DFS控制器
 */
@Tag(description = "SysProfileController", name = "DFS控制器")
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends TWTController {

	@Autowired
	private ISysUserService userService;

	/**
	 * 个人信息
	 * @return JsonResult<UserInfoVo>
	 */
	@Operation(summary = "个人信息")
	@GetMapping
	public JsonResult<UserInfoVo> profile() {
		String username = SecurityUtils.getUsername();
		SysUser user = userService.selectUserByUserName(username, true);

		UserInfoVo userInfoVo = new UserInfoVo();

		userInfoVo.setUser(user);
		userInfoVo.setPostGroup(userService.selectUserPostGroup(username));
		userInfoVo.setRoleGroup(userService.selectUserRoleGroup(username));

		return JsonResult.success(userInfoVo);
	}

	/**
	 * 修改当前用户信息
	 * @param user SysUser
	 * @return 修改结果
	 */
	@Operation(summary = "修改当前用户信息")
	@Log(service = "个人信息", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> updateProfile(@RequestBody SysUser user) {
		Long userId = SecurityUtils.getLoginUser().getUserId();
		user.setUserId(userId);
		if (userService.updateUserProfile(user) > 0) {
			return JsonResult.success();
		}
		return JsonResult.error("修改个人信息异常，请联系管理员");
	}

	/**
	 * 修改用户头像
	 * @param file MultipartFile
	 * @return 上传信息
	 */
	/*
	 * @Operation(summary = "修改用户头像")
	 *
	 * @Log(service = "用户头像", businessType = BusinessType.UPDATE)
	 *
	 * @PostMapping("/avatar") public AjaxResult avatar(@RequestParam("avatarFile")
	 * MultipartFile file) {
	 *
	 * try { R<SysFile> fileResult = remoteFileService.upload(file,
	 * SecurityConstants.INNER);
	 *
	 * if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
	 * return AjaxResult.error("文件服务异常，请联系管理员"); }
	 *
	 * String url = fileResult.getData().getUrl();
	 *
	 * LoginUser user = SecurityUtils.getLoginUser();
	 *
	 * if (userService.updateUserAvatar(user.getUsername(), url)) { AjaxResult ajax =
	 * AjaxResult.success("设置成功"); ajax.put("imgUrl", url); return ajax; } } catch
	 * (Exception e) { logger.error("上传头像失败：", e); return AjaxResult.error("发生未知错误"); }
	 * return AjaxResult.error("上传失败"); }
	 */

	/**
	 * 重置密码
	 * @param userPassword 用户修改密码参数
	 * @return 重置结果
	 */
	@Operation(summary = "重置密码")
	@Log(service = "个人信息", businessType = BusinessType.UPDATE)
	@PutMapping("/updatePwd")
	public JsonResult<String> updatePwd(@RequestBody UserPassword userPassword) {

		if (!userPassword.getNewPassword().equals(userPassword.getConfirmPassword())) {
			return JsonResult.error("确认密码不一致");
		}

		String username = SecurityUtils.getUsername();
		SysUser user = userService.selectUserByUserName(username, true);
		String password = user.getPassword();

		if (!BCrypt.checkpw(userPassword.getOldPassword(), password)) {
			return JsonResult.error("修改密码失败，旧密码错误");
		}

		if (BCrypt.checkpw(userPassword.getNewPassword(), password)) {
			return JsonResult.error("新密码不能与旧密码相同");
		}

		if (userService.resetUserPwd(username, BCrypt.hashpw(userPassword.getNewPassword())) > 0) {
			return JsonResult.success();
		}

		return JsonResult.error("修改密码异常，请联系管理员");
	}

}