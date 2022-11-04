package com.twelvet.framework.swagger.config;

import com.twelvet.framework.swagger.config.properties.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.*;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.providers.JavadocProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: swagger配置
 */
@Configuration
@AutoConfigureBefore(SpringDocConfiguration.class)
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    @ConditionalOnMissingBean(OpenAPI.class)
    public OpenAPI openApi() {
        OpenAPI openAPI = new OpenAPI();
        // 文档基本信息
        SwaggerProperties.InfoProperties infoProperties = swaggerProperties.getInfo();
        Info info = convertInfo(infoProperties);
        openAPI.info(info);
        // 扩展文档信息
        openAPI.externalDocs(swaggerProperties.getExternalDocs());
        openAPI.tags(swaggerProperties.getTags());
        openAPI.paths(swaggerProperties.getPaths());
        openAPI.components(swaggerProperties.getComponents());
        List<SecurityRequirement> list = new ArrayList<>();
        list.add(new SecurityRequirement().addList("apikey"));
        openAPI.security(list);

        return openAPI;
    }

    private Info convertInfo(SwaggerProperties.InfoProperties infoProperties) {
        Info info = new Info();
        info.setTitle(infoProperties.getTitle());
        info.setDescription(infoProperties.getDescription());
        info.setContact(infoProperties.getContact());
        info.setLicense(infoProperties.getLicense());
        info.setVersion(infoProperties.getVersion());
        return info;
    }

    /**
     * 对已经生成好的 OpenApi 进行自定义操作
     */
    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        String contextPath = serverProperties.getServlet().getContextPath();
        String finalContextPath;
        if (StringUtils.isBlank(contextPath) || "/".equals(contextPath)) {
            finalContextPath = "";
        } else {
            finalContextPath = contextPath;
        }
        // 对所有路径增加前置上下文路径
        return openApi -> {
            Paths oldPaths = openApi.getPaths();
            if (oldPaths instanceof PlusPaths) {
                return;
            }
            PlusPaths newPaths = new PlusPaths();
            oldPaths.forEach((k, v) -> newPaths.addPathItem(finalContextPath + k, v));
            openApi.setPaths(newPaths);
        };
    }

    /**
     * 单独使用一个类便于判断 解决springdoc路径拼接重复问题
     */
    static class PlusPaths extends Paths {

        public PlusPaths() {
            super();
        }
    }

}