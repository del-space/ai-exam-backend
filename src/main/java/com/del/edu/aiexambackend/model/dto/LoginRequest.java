package com.del.edu.aiexambackend.model.dto;


import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录 DTO
 *
 * @author: Del
 * @date: 2026-09
 */
@Data
public class LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7974150687301867681L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 验证码
     */
    private String captchaId;

    /**
     * 行为验证码轨迹
     */
    private ImageCaptchaTrack track;
}
