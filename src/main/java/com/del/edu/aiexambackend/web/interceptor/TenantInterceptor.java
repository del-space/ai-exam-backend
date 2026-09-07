package com.del.edu.aiexambackend.web.interceptor;


import com.del.edu.aiexambackend.common.context.TenantContext;
import com.del.edu.aiexambackend.common.context.UserContext;
import com.del.edu.aiexambackend.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 租户拦截器
 *
 * @author: Del
 * @date: 2026-09
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    /**
     * 请求前置处理方法
     * 在请求处理之前进行拦截处理，主要用于解析JWT令牌并设置租户和用户上下文
     * @param request 当前HTTP请求对象
     * @param response 当前HTTP响应对象
     * @param handler 请求处理方法
     * @return 返回true表示继续流程，false表示终端流程
     * @throws Exception 可能抛出的异常
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 从请求头中获取Authorization字段
        String auth = request.getHeader("Authorization");
        // 检查Authorization字段是否存在且以"Bearer "开头
        if (auth != null && auth.startsWith("Bearer ")) {
            // 提取JWT令牌（去掉"Bearer "前缀）
            String token = auth.substring(7);
            // 解析令牌中的租户ID并设置到租户上下文中
            TenantContext.setTenantId(JwtUtil.parseTenantId(token));
            // 解析令牌中的用户ID并设置到用户上下文中
            UserContext.setUserId(JwtUtil.parseUserId(token));
        }
        return true; // 继续后续处理流程
    }

    /**
     * 请求完成后处理方法
     * 在请求完成后进行清理工作，主要用于清除租户和用户上下文
     * @param request 当前HTTP请求对象
     * @param response 当前HTTP响应对象
     * @param handler 请求处理方法
     * @param ex 处理过程中发生的异常（如果有）
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) throws Exception {
        // 清除租户上下文
        TenantContext.clear();
        // 清除用户上下文
        UserContext.clear();
    }
}
