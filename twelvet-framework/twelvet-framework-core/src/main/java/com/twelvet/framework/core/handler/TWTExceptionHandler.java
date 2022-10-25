package com.twelvet.framework.core.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.exception.TWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
public class TWTExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(TWTExceptionHandler.class);

    /**
     * 未登录拦截
     * 更多拦截处理：<a href="https://gitee.com/dromara/sa-token/blob/master/sa-token-demo/sa-token-demo-case/src/main/java/com/pj/current/GlobalException.java">...</a>
     *
     * @param e Exception
     * @return JsonResult<String>
     */
    @ExceptionHandler(NotLoginException.class)
    public JsonResult<String> handlerException(NotLoginException e) {
        return JsonResult.error(e.getMessage());
    }

    /**
     * 全局异常
     *
     * @param e Exception
     * @return AjaxResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return JsonResult.error(e.getLocalizedMessage());
    }

    /**
     * 基础异常
     *
     * @param e TWTException
     * @return AjaxResult
     */
    @ExceptionHandler(TWTException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<String> fastGoException(TWTException e) {
        return JsonResult.error(e.getMessage());
    }

    /**
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     *
     * @param exception 参数校验异常
     * @return API返回结果对象包装后的错误输出结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("非法参数,ex = {}", exception.getMessage(), exception);
        return JsonResult.error(exception.getMessage());
    }

    /**
     * validation Exception 参数绑定异常
     *
     * @return AjaxResult
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult<String> handleBodyValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return JsonResult.error(fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * validation Exception (以form-data形式传参) 参数绑定异常
     *
     * @return AjaxResult
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult<String> bindExceptionHandler(BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.error("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return JsonResult.error(fieldErrors.get(0).getDefaultMessage());
    }

}
