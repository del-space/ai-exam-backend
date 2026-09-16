package com.del.edu.aiexambackend.web.controller.auth;

import com.del.edu.aiexambackend.common.BaseResponse;
import com.del.edu.aiexambackend.common.utils.result.ResultUtils;
import com.del.edu.aiexambackend.model.dto.LoginRequest;
import com.del.edu.aiexambackend.model.dto.LoginResponse;
import com.del.edu.aiexambackend.model.dto.RegisterRequest;
import com.del.edu.aiexambackend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 *
 * @author: Del
 * @date: 2026-09
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证接口")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request,
                                             HttpServletRequest httpRequest) {
        return ResultUtils.success(authService.login(request, getClientIp(httpRequest)));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public BaseResponse<String> register(@RequestBody RegisterRequest request) {
        return ResultUtils.success(authService.register(request));
    }

    /**
     * 获取客户端真实 IP（兼容反向代理场景）
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    private boolean isInvalidIp(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }
}
