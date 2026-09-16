package com.del.edu.aiexambackend.model.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录响应
 *
 * @author: Del
 * @date: 2026-09
 */
@Data
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 4913916390186148082L;

    /** JWT 令牌 */
    private String token;

    /** 过期时间戳（毫秒） */
    private Long expiresAt;

    /** 用户信息（脱敏后返回给前端） */
    private UserInfo userInfo;

    /**
     * 用户信息（脱敏后返回给前端）
     */
    @Data
    public static class UserInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 36346032983701539L;

        /** 用户ID */
        private String id;

        /** 租户ID */
        private String tenantId;

        /** 用户名 */
        private String username;

        /** 真实姓名 */
        private String realName;

        /** 邮箱 */
        private String email;

        /** 手机号 */
        private String phone;

        /** 用户角色 */
        private String role;
    }
}
