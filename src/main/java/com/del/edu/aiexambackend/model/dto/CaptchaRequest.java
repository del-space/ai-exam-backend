package com.del.edu.aiexambackend.model.dto;


import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 验证码请求
 *
 * @author: Del
 * @date: 2026-09
 */
@Data
public class CaptchaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -162988221213392664L;

    /**
     * 验证码 ID（生成时返回）
     */
    private String captchaId;

    /**
     * 用户操作轨迹
     */
    private ImageCaptchaTrack track;
}
