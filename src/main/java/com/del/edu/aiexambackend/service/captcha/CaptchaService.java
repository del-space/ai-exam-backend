package com.del.edu.aiexambackend.service.captcha;

import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import com.del.edu.aiexambackend.model.dto.CaptchaRequest;

/**
 * 验证码服务接口
 *
 * @author: Del
 * @date: 2026-09
 */
public interface CaptchaService {

    /**
     * 生成验证码
     * @return 验证码对象（包含图片 base64， 验证码id等）
     */
    ImageCaptchaVO generate();

    /**
     * 验证验证码
     * @param captchaId 验证码id
     * @param request 验证码请求对象
     * @return 是否验证成功
     */
    boolean validate(String captchaId, CaptchaRequest request);
}
