package com.twelvet.system.api.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 路由配置信息
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 路由名字
	 */
	private String name;

	/**
	 * 路由地址
	 */
	private String path;

	/**
	 * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
	 */
	private boolean hidden;

	/**
	 * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
	 */
	private String redirect;

	/**
	 * 组件地址
	 */
	private String component;

	/**
	 * 当你一个路由下面的 routes 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
	 */
	private Boolean alwaysShow;

	/**
	 * icon
	 */
	private String icon;

	/**
	 * 类型（M目录 C菜单 F按钮）
	 */
	private String menuType;

	/**
	 * 其他元素
	 */
	private MetaVo meta;

	/**
	 * 子路由
	 */
	private List<RouterVo> routes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean getHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Boolean getAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(Boolean alwaysShow) {
		this.alwaysShow = alwaysShow;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public MetaVo getMeta() {
		return meta;
	}

	public void setMeta(MetaVo meta) {
		this.meta = meta;
	}

	public List<RouterVo> getRoutes() {
		return routes;
	}

	public void setRoutes(List<RouterVo> routes) {
		this.routes = routes;
	}

}
