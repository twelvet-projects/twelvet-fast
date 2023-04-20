package com.twelvet.framework.swagger.config.properties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: swagger 配置属性
 */
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

	/**
	 * 是否开启 openApi 文档
	 */
	private Boolean enabled = true;

	/**
	 * 文档基本信息
	 */
	@NestedConfigurationProperty
	private InfoProperties info = new InfoProperties();

	/**
	 * 扩展文档地址
	 */
	@NestedConfigurationProperty
	private ExternalDocumentation externalDocs;

	/**
	 * 标签
	 */
	private List<Tag> tags = null;

	/**
	 * 路径
	 */
	@NestedConfigurationProperty
	private Paths paths = null;

	/**
	 * 组件
	 */
	@NestedConfigurationProperty
	private Components components = null;

	/**
	 * <p>
	 * 文档的基础属性信息
	 * </p>
	 *
	 * @see io.swagger.v3.oas.models.info.Info
	 *
	 * 为了 springboot 自动生产配置提示信息，所以这里复制一个类出来
	 */
	public static class InfoProperties {

		/**
		 * 标题
		 */
		private String title = null;

		/**
		 * 描述
		 */
		private String description = null;

		/**
		 * 联系人信息
		 */
		@NestedConfigurationProperty
		private Contact contact = null;

		/**
		 * 许可证
		 */
		@NestedConfigurationProperty
		private License license = null;

		/**
		 * 版本
		 */
		private String version = null;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Contact getContact() {
			return contact;
		}

		public void setContact(Contact contact) {
			this.contact = contact;
		}

		public License getLicense() {
			return license;
		}

		public void setLicense(License license) {
			this.license = license;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public InfoProperties getInfo() {
		return info;
	}

	public void setInfo(InfoProperties info) {
		this.info = info;
	}

	public ExternalDocumentation getExternalDocs() {
		return externalDocs;
	}

	public void setExternalDocs(ExternalDocumentation externalDocs) {
		this.externalDocs = externalDocs;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Paths getPaths() {
		return paths;
	}

	public void setPaths(Paths paths) {
		this.paths = paths;
	}

	public Components getComponents() {
		return components;
	}

	public void setComponents(Components components) {
		this.components = components;
	}

}
