package com.del.edu.aiexambackend.web.interceptor;


import com.del.edu.aiexambackend.common.context.TenantContext;
import com.del.edu.aiexambackend.common.context.UserContext;
import com.del.edu.aiexambackend.common.utils.security.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

/**
 * 租户拦截器
 *
 * @author: Del
 * @date: 2026-09
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    /** 不需要登录就能访问的接口白名单（已经是完整路径，含 /api 前缀） */
    private static final Set<String> WHITE_LIST = Set.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/captcha/generate",
            "/api/captcha/verify"
    );

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
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        // 获取请求的URL
        String url = request.getRequestURI();

        // 从请求头中获取Authorization字段
        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            if (WHITE_LIST.contains(url)) {
                return true;
            }
            writeUnauthorized(response, "未登录");
            return false;
        }

        // 带了 token：尝试解析，失败一律返回 401
        String token = auth.substring(7);
        try {
            String tenantId = jwtUtil.parseTenantId(token);
            String userId = jwtUtil.parseUserId(token);
            TenantContext.setTenantId(tenantId);
            UserContext.setUserId(userId);
            return true;
        } catch (Exception e) {
            writeUnauthorized(response, "登录已过期或 token 无效");
            return false;
        }
    }


    /**
     * 向客户端返回未授权错误响应
     * @param response HttpServletResponse对象，用于向客户端发送响应
     * @param message 错误信息，将被包含在响应中
     * @throws Exception 可能由response.getWriter()操作抛出的异常
     */
    private void writeUnauthorized(HttpServletResponse response, String message) throws Exception {
        // 设置HTTP响应状态码为401（未授权）
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应内容类型为JSON，并指定字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        // 向响应输出流写入JSON格式的错误信息
        response.getWriter().write("{\"code\":401,\"message\":\"" + message + "\"}");
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
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                @Nullable Exception ex) throws Exception {
        // 清除租户上下文
        TenantContext.clear();
        // 清除用户上下文
        UserContext.clear();
    }
}
