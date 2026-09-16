package com.del.edu.aiexambackend.service.auth;


import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.del.edu.aiexambackend.common.utils.result.ThrowUtils;
import com.del.edu.aiexambackend.exception.BusinessException;
import com.del.edu.aiexambackend.exception.ErrorCode;
import com.del.edu.aiexambackend.model.dto.CaptchaRequest;
import com.del.edu.aiexambackend.model.dto.LoginRequest;
import com.del.edu.aiexambackend.model.dto.LoginResponse;
import com.del.edu.aiexambackend.model.dto.RegisterRequest;
import com.del.edu.aiexambackend.service.base.UserService;
import com.del.edu.aiexambackend.service.captcha.CaptchaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现
 *
 * @author: Del
 * @date: 2026-09
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private CaptchaService captchaService;

    @Resource
    private UserService userService;

    @Override
    public LoginResponse login(LoginRequest loginRequest, String ip) {
        // 1. 校验行为验证码
        verifyCaptcha(loginRequest.getCaptchaId(), loginRequest.getTrack());

        // 2. 委托给用户服务执行业务登录（租户校验 + 密码校验 + 生成 JWT 等）
        return userService.login(
                loginRequest.getTenantCode(),
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                ip
        );
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        // 1. 校验行为验证码
        verifyCaptcha(registerRequest.getCaptchaId(), registerRequest.getTrack());

        // 2. 委托给用户服务执行业务注册（参数校验 + 用户名查重 + 密码哈希入库）
        return userService.register(registerRequest);
    }

    /**
     * 行为验证码校验（抽成私有方法，登录 / 注册复用）
     *
     * @param captchaId 验证码 ID
     * @param track     用户操作轨迹
     */
    private void verifyCaptcha(String captchaId, ImageCaptchaTrack track) {
        ThrowUtils.throwIf(captchaId == null || captchaId.isBlank(),
                ErrorCode.CAPTCHA_VERIFY_ERROR, "验证码ID不能为空");

        CaptchaRequest captchaRequest = new CaptchaRequest();
        captchaRequest.setCaptchaId(captchaId);
        captchaRequest.setTrack(track);

        boolean ok = captchaService.validate(captchaId, captchaRequest);
        if (!ok) {
            throw new BusinessException(ErrorCode.CAPTCHA_VERIFY_ERROR);
        }
    }
}