package com.del.edu.aiexambackend.common.context;


/**
 * 用户上下文
 *
 * @author: Del
 * @date: 2026-09
 */
public class UserContext {

    /**
     * 使用ThreadLocal存储用户ID的工具类
     * ThreadLocal用于实现线程内的变量共享，确保线程安全
     */
    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();

    /**
     * 获取当前线程的用户ID
     * @return 当前线程的用户ID，如果未设置则返回null
     */
    public static String getUserId() {
        return USER_ID.get();
    }

    /**
     * 设置当前线程的用户ID
     * @param userId 要设置的用户ID
     */
    public static void setUserId(String userId) {
        USER_ID.set(userId);
    }

    /**
     * 清除当前线程的用户ID
     * 在使用完ThreadLocal变量后应调用此方法，防止内存泄漏
     */
    public static void clear() {
        USER_ID.remove();
    }
}
