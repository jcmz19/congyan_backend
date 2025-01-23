package com.congyan.controller.exception;

import com.congyan.entity.RestBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * 专用用于处理错误页面的Controller
 */
@RestController
@RequestMapping({"/error"})
public class ErrorPageController extends AbstractErrorController {

    public ErrorPageController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * http错误在这里统一处理，自动解析状态码和原因
     * @param request 请求
     * @return 失败响应原因
     */
    @RequestMapping
    public RestBean<Void> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        Map<String, Object> errorAttributes = this.getErrorAttributes(request, this.getAttributeOptions());
        String message = this.convertErrorMessage(status)
                .orElse(errorAttributes.get("message").toString());
        return RestBean.failure(status.value(), message);
    }

    /**
     * 查询code错误码对应的原因
     * @param code 错误码
     * @return 错误码对应的原因
     */
    @GetMapping("/{code}")
    public RestBean<String> codeError(@PathVariable int code){
        String message = this.convertCodeErrorMessage(code)
                .orElse("on such error-code");
        return RestBean.success(message);
    }

    /**
     * 对于状态码，错误信息转换
     * @param status 状态码
     * @return 错误信息
     */
    private Optional<String> convertErrorMessage(HttpStatus status){
        String value = switch (status.value()) {
            case 400 -> "请求参数有误";
            case 404 -> "请求的接口不存在";
            case 405 -> "请求方法错误";
            case 500 -> "内部错误，请联系管理员";
            default -> null;
        };
        return Optional.ofNullable(value);
    }

    /**
     * 对于一些特殊的状态码，错误信息转换
     * @param code 状态码
     * @return 错误信息
     */
    private Optional<String> convertCodeErrorMessage(int code){
        String value = switch (code) {
            case 1001 -> "base64文件写入失败，请联系管理员";
            case 1002 -> "数据库问题，请联系管理员";
            case 1003 -> "文件写入失败，请联系管理员";
            case 2001 -> "请求频繁";
            case 2002 -> "无权限";
            case 2003 -> "注册或登录格式错误";
            case 2004 -> "空音频文件";
            case 2005 -> "拼音解析失败，请联系管理员";
            case 2006 -> "没有此目录";
            case 3001 -> "tts工具网络错误，请联系管理员";
            case 3002 -> "音频识别出错，请联系管理员";
            default -> null;
        };
        return Optional.ofNullable(value);
    }

    /**
     * 错误属性获取选项，这里额外添加了错误消息和异常类型
     * @return 选项
     */
    private ErrorAttributeOptions getAttributeOptions(){
        return ErrorAttributeOptions
                .defaults()
                .including(ErrorAttributeOptions.Include.MESSAGE,
                        ErrorAttributeOptions.Include.EXCEPTION);
    }
}
