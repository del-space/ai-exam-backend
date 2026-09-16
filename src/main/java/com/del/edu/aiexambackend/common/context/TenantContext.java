package com.del.edu.aiexambackend.common.context;


/**
 * 多租户上下文
 *
 * @author: Del
 * @date: 2026-09
 */
public class TenantContext {

    /**
     * 使用ThreadLocal来存储租户ID的工具类
     * ThreadLocal可以保证每个线程都有自己的租户ID副本，避免多线程环境下的数据共享问题
     */
    private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();

    /**
     * 设置当前线程的租户ID
     * @param tenantId 要设置的租户ID
     */
    public static void setTenantId(String tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * 获取当前线程的租户ID
     * @return 当前线程的租户ID，如果未设置则返回null
     */
    public static String getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * 清除当前线程的租户ID
     * 在使用完租户ID后应该调用此方法，防止内存泄漏
     */
    public static void clear() {
        TENANT_ID.remove();
    }
}