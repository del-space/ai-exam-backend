package com.del.edu.aiexambackend.exception;


import lombok.Getter;

/**
 * @author: Del
 * @date: 2026-03-25
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;


    /**
     * 自定义业务异常类的构造方法
     * @param code 业务错误码
     * @param message 错误信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 自定义业务异常类的构造方法
     * @param errorCode 错误码枚举
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 自定义业务异常类的构造方法
     * @param errorCode 错误码枚举
     * @param message 错误信息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
