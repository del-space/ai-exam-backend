package com.del.edu.aiexambackend.config.bootstrap;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 启动自检：超级管理员初始化的配置项
 *
 * 参考现有风格：JwtUtil.java (27-32) 用 @Value 注入
 */
@Getter
@Configuration
public class SuperAdminBootstrapProperties {

    @Value("${bootstrap.super-admin.enabled:true}")
    private boolean enabled;

    @Value("${bootstrap.super-admin.tenant-code:PLATFORM}")
    private String tenantCode;

    @Value("${bootstrap.super-admin.tenant-name:Platform}")
    private String tenantName;

    @Value("${bootstrap.super-admin.username:}")
    private String username;

    @Value("${bootstrap.super-admin.password:}")
    private String password;

    @Value("${bootstrap.super-admin.real-name:超级管理员}")
    private String realName;

}