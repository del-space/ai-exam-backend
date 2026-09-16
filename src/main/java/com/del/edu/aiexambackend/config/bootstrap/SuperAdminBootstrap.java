package com.del.edu.aiexambackend.config.bootstrap;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.del.edu.aiexambackend.common.utils.security.BCryptUtil;
import com.del.edu.aiexambackend.common.utils.id.ModuleIdGenerator;
import com.del.edu.aiexambackend.config.handler.MyTenantLineHandler;
import com.del.edu.aiexambackend.model.entity.Tenant;
import com.del.edu.aiexambackend.model.entity.User;
import com.del.edu.aiexambackend.service.base.TenantService;
import com.del.edu.aiexambackend.service.base.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 启动自检：自动初始化"平台租户 + 超级管理员账号"。
 *
 * 多租户适配（基于 MyTenantLineHandler.java）：
 *   启动时 TenantContext 是空的，且没必要塞占位符 —
 *   我们直接打开 MyTenantLineHandler 的 ignoreTenantOn()，让 MP 在当前线程
 *   完全跳过租户 SQL 拼装。完毕后在 finally 里恢复。
 *
 * @author: Del
 * @date: 2026-09
 */
@Slf4j
@Component
public class SuperAdminBootstrap implements ApplicationRunner {

    private static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";

    @Resource
    private SuperAdminBootstrapProperties props;

    @Resource
    private TenantService tenantService;

    @Resource
    private UserService userService;

    @Resource
    private ModuleIdGenerator moduleIdGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(@NonNull ApplicationArguments args) {
        if (!props.isEnabled()) {
            log.info("[SuperAdminBootstrap] 已禁用，跳过");
            return;
        }

        // 打开"忽略租户拦截"开关，本线程所有 SQL 都不拼 tenant_id
        MyTenantLineHandler.ignoreTenantOn();
        try {
            // 1) 已有超管 → 直接返回
            long existed = userService.count(
                    new LambdaQueryWrapper<User>().eq(User::getRole, ROLE_SUPER_ADMIN));
            if (existed > 0) {
                log.info("[SuperAdminBootstrap] 已检测到 SUPER_ADMIN（count={}），跳过初始化", existed);
                return;
            }

            // 2) 必填校验
            if (isBlank(props.getUsername()) || isBlank(props.getPassword())) {
                log.warn("[SuperAdminBootstrap] bootstrap.super-admin.username/password 未配置，跳过");
                return;
            }

            // 3) 确保"平台租户"存在
            Tenant platform = tenantService.getOne(
                    new LambdaQueryWrapper<Tenant>().eq(Tenant::getCode, props.getTenantCode()));
            if (platform == null) {
                platform = new Tenant();
                platform.setId(moduleIdGenerator.generateModuleId("tenant"));
                platform.setName(props.getTenantName());
                platform.setCode(props.getTenantCode());
                platform.setStatus(1);
                tenantService.save(platform);
                log.info("[SuperAdminBootstrap] 已创建平台租户 code={}", platform.getCode());
            }

            // 4) 创建超管用户
            User admin = new User();
            admin.setId(moduleIdGenerator.generateModuleId("user"));
            admin.setTenantId(platform.getId());
            admin.setUsername(props.getUsername());
            admin.setPassword(BCryptUtil.hashPassword(props.getPassword()));
            admin.setRealName(props.getRealName());
            admin.setRole(ROLE_SUPER_ADMIN);
            admin.setEnabled(true);
            userService.save(admin);

            log.warn("""
                    [SuperAdminBootstrap] ✅ 已自动初始化 SUPER_ADMIN：
                      platformTenant = {}
                      username       = {}
                      ⚠️ 请尽快登录并修改初始密码！""",
                    platform.getCode(), admin.getUsername());

        } finally {
            // 必须关掉，否则万一这线程被复用会污染
            MyTenantLineHandler.ignoreTenantOff();
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}