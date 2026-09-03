package com.del.edu.aiexambackend.exception;


import com.del.edu.aiexambackend.common.BaseResponse;
import com.del.edu.aiexambackend.common.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: Del
 * @date: 2026-03-25
 * @description: 全局异常处理
 */
@RestControllerAdvice
@Hidden
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * @param e 业务异常
     * @return 错误信息
     */
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统异常
     * @param e 系统异常
     * @return 错误信息
     */
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse<?> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage());
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
}

