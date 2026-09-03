package com.del.edu.aiexambackend.common;


import com.del.edu.aiexambackend.exception.ErrorCode;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: Del
 * @date: 2026-03-25
 * @description: 全局响应封装类
 */
@Data
public class BaseResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -6121882283544514845L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 构造方法
     *
     * @param code    状态码
     * @param data    数据
     * @param message 错误信息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param code    状态码
     * @param data    数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 构造方法
     *
     * @param errorCode 错误码
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
