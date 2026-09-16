package com.del.edu.aiexambackend.model.dto;


import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注册请求
 *
 * @author: Del
 * @date: 2026-09
 */
@Data
public class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户名（8-13位，字母开头，仅字母和数字） */
    private String username;

    /** 密码（6-11位） */
    private String password;

    /** 租户编码 */
    private String tenantCode;

    /** 验证码 ID */
    private String captchaId;

    /** 行为验证码轨迹 */
    private ImageCaptchaTrack track;
}
