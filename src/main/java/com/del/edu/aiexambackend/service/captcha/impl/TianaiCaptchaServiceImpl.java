package com.del.edu.aiexambackend.service.captcha.impl;


import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import com.del.edu.aiexambackend.common.utils.result.ThrowUtils;
import com.del.edu.aiexambackend.exception.BusinessException;
import com.del.edu.aiexambackend.exception.ErrorCode;
import com.del.edu.aiexambackend.model.dto.CaptchaRequest;
import com.del.edu.aiexambackend.service.captcha.CaptchaService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 验证码服务
 *
 * @author: Del
 * @date: 2026-09
 */
@Service
@Slf4j
public class TianaiCaptchaServiceImpl implements CaptchaService {

    @Resource
    private ImageCaptchaApplication captchaApp;

    @Override
    public boolean validate(String captchaId, CaptchaRequest request) {

        ThrowUtils.throwIf(request == null, ErrorCode.PARAM_ERROR);

        // 1. 验证验证码
        ApiResponse<?> response = captchaApp.matching(captchaId, request.getTrack());

        ThrowUtils.throwIf(!response.isSuccess(), ErrorCode.OPERATION_ERROR, "验证码校验失败");

        // 2. 校验结果
        return true;
    }

    @Override
    public ImageCaptchaVO generate() {
        // 1. 生成验证码
        ApiResponse<ImageCaptchaVO> response = captchaApp.generateCaptcha(CaptchaTypeConstant.SLIDER);

        // 2. 校验
        ThrowUtils.throwIf(response == null || !response.isSuccess(),
                new BusinessException(ErrorCode.OPERATION_ERROR, "验证码生成失败"));

        // 3. 返回结果
        return response.getData();
    }
}
