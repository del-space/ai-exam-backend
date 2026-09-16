package com.del.edu.aiexambackend.web.controller.captcha;


import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import com.del.edu.aiexambackend.common.BaseResponse;
import com.del.edu.aiexambackend.common.utils.result.ResultUtils;
import com.del.edu.aiexambackend.model.dto.CaptchaRequest;
import com.del.edu.aiexambackend.service.captcha.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制器
 *
 * @author: Del
 * @date: 2026-09
 */
@RestController
@RequestMapping("/captcha")
@Tag(name = "验证码接口")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    /**
     * 生成验证码
     */
    @PostMapping("/generate")
    @Operation(summary = "生成行为验证码")
    public BaseResponse<ImageCaptchaVO> generate() {
        return ResultUtils.success(captchaService.generate());
    }

    /**
     * 校验验证码
     */
    @PostMapping("/verify")
    @Operation(summary = "校验行为验证码")
    public BaseResponse<Boolean> verify(@RequestBody CaptchaRequest request) {
        return ResultUtils.success(captchaService.validate(request.getCaptchaId(), request));
    }
}
