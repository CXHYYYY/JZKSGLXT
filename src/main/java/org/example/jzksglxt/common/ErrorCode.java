package org.example.jzksglxt.common;

public enum ErrorCode {
    /**
     * 成功
     */
    SUCCESS(200, "success"),
    /**
     * 失败
     */
    ERROR(500, "error"),
    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),
    /**
     * 未登录
     */
    NOT_LOGIN(401, "未登录"),
    /**
     * 无权限
     */
    NO_PERMISSION(403, "无权限"),
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
    /**
     * 用户名已存在
     */
    USERNAME_EXIST(400, "用户名已存在"),
    /**
     * 用户名或密码错误
     */
    USERNAME_PASSWORD_ERROR(400, "用户名或密码错误"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(404, "用户不存在"),
    /**
     * 密码重置失败
     */
    PASSWORD_RESET_ERROR(500, "密码重置失败"),
    /**
     * 用户删除失败
     */
    USER_DELETE_ERROR(500, "用户删除失败"),
    /**
     * 用户更新失败
     */
    USER_UPDATE_ERROR(500, "用户更新失败"),
    /**
     * 用户创建失败
     */
    USER_CREATE_ERROR(500, "用户创建失败");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}