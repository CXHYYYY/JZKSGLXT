package org.example.jzksglxt.common;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * Getter for code
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Setter for code
     * @param code code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Getter for message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for message
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for data
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter for data
     * @param data data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功返回结果
     * @param data 数据
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /**
     * 成功返回结果
     * @param data 数据
     * @param message 消息
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 成功返回结果
     * @param message 消息
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(ErrorCode errorCode) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(errorCode.getCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 消息
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(ErrorCode errorCode, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(errorCode.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 失败返回结果
     * @param code 状态码
     * @param message 消息
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(Integer code, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败返回结果
     * @param message 消息
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}