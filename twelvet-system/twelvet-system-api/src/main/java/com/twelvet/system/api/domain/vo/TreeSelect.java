package com.twelvet.system.api.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.twelvet.system.api.domain.SysDept;
import com.twelvet.system.api.domain.SysMenu;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: TreeSelect树结构实体类
 */
public class TreeSelect implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 节点ID
	 */
	private Long key;

	/**
	 * 节点名称
	 */
	private String title;

	/**
	 * 节点内容
	 */
	private Long value;

	/**
	 * 子节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<TreeSelect> children;

	public TreeSelect() {

	}

	public TreeSelect(SysDept dept) {
		this.title = dept.getDeptName();
		this.value = dept.getDeptId();
		this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	public TreeSelect(SysMenu menu) {
		this.key = menu.getMenuId();
		this.title = menu.getMenuName();
		this.children = menu.getRoutes().stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long id) {
		this.key = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public List<TreeSelect> getChildren() {
		return children;
	}

	public void setChildren(List<TreeSelect> children) {
		this.children = children;
	}

}
