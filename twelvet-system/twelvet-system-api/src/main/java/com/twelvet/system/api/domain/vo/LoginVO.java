package com.twelvet.system.api.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 登录后信息
 */
@Schema(description = "登录后信息")
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "code")
    private int code = HttpStatus.OK.value();

    /**
     * 认证token
     */
    @JsonProperty("access_token")
    @Schema(description = "认证token")
    private String accessToken;

    /**
     * 刷新token
     */
    @JsonProperty("refresh_token")
    @Schema(description = "刷新token")
    private String refreshToken;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
