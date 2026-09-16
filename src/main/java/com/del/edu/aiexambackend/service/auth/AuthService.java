package com.del.edu.aiexambackend.service.auth;


import com.del.edu.aiexambackend.model.dto.LoginRequest;
import com.del.edu.aiexambackend.model.dto.LoginResponse;
import com.del.edu.aiexambackend.model.dto.RegisterRequest;

/**
 *  认证服务接口
 *
 * @author: Del
 * @date: 2026-09
 */
public interface AuthService {

    /**
     * 登录方法，用于处理用户登录请求
     * @param loginRequest 登录请求对象，包含用户名、密码等登录信息
     * @param ip 客户端IP地址，用于记录登录位置等信息
     * @return LoginResponse 登录响应对象，包含登录结果、用户信息、token等数据
     */
    LoginResponse login(LoginRequest loginRequest, String ip);

    /**
     * 处理用户注册请求的方法
     * @param registerRequest 包含用户注册所需信息的请求对象
     * @return 返回注册结果信息，通常是成功或失败的提示
     */
    String register(RegisterRequest registerRequest);
}
