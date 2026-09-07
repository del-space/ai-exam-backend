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
     */
    private static final List<String> IGNORE_TABLES = Arrays.asList(
            "tenant",           // 租户表本身
            "class_student",    // 无 tenant_id 列
            "exam_question"     // 无 tenant_id 列
    );

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
        return IGNORE_TABLES.contains(tableName);
    }
}
