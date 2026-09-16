package com.del.edu.aiexambackend.config.handler;


import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.del.edu.aiexambackend.common.context.TenantContext;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 多租户处理器
 *
 * @author: Del
 * @date: 2026-09
 */
@Component
public class MyTenantLineHandler implements TenantLineHandler {
    /**
     * 没有 tenant_id 列的表，必须忽略，否则 MP 拼 WHERE tenant_id = ? 会报列不存在
     * 注意：表名要和实体类 @TableName 完全一致（带 schema 前缀 + 双引号转义）
     */
    private static final List<String> IGNORE_TABLES = Arrays.asList(
            "exam.\"tenant\"",        // 租户表本身
            "exam.\"class_student\"", // 无 tenant_id 列
            "exam.\"exam_question\""  // 无 tenant_id 列
    );

    /**
     * 定义一个ThreadLocal类型的静态常量IGNORE_TENANT，用于存储是否忽略租户标识的布尔值
     * 初始值设为false，表示默认不忽略租户标识
     */
    private static final ThreadLocal<Boolean> IGNORE_TENANT = ThreadLocal.withInitial(() -> false);

    /**
     * 开启忽略租户标识的方法
     * 将IGNORE_TENANT的值设置为true，表示当前线程忽略租户标识
     */
    public static void ignoreTenantOn() {
        IGNORE_TENANT.set(true);
    }

    /**
     * 关闭忽略租户标识的方法
     * 将IGNORE_TENANT的值设置为false，表示当前线程不忽略租户标识
     */
    public static void ignoreTenantOff() {
        IGNORE_TENANT.set(false);
    }

    @Override
    public Expression getTenantId() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null || tenantId.isEmpty()) {
            throw new RuntimeException("tenantId is null, please check login interceptor");
        }
        return new StringValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        if (Boolean.TRUE.equals(IGNORE_TENANT.get())) {
            return true;
        }
        return IGNORE_TABLES.contains(tableName);
    }
}
