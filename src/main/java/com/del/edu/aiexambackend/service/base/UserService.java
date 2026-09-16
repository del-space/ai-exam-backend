package com.del.edu.aiexambackend.service.base;

import com.baomidou.mybatisplus.spring.service.IService;
import com.del.edu.aiexambackend.model.dto.LoginResponse;
import com.del.edu.aiexambackend.model.dto.RegisterRequest;
import com.del.edu.aiexambackend.model.entity.User;

/**
 * 用户服务
 *
 * @author: Del
 * @date: 2026-09
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param tenantCode 租户编码
     * @param username   用户名
     * @param password   明文密码
     * @param ip         客户端 IP（用于记录登录信息）
     * @return 登录响应（token + 过期时间 + 用户信息）
     */
    LoginResponse login(String tenantCode, String username, String password, String ip);

    /**
     * 用户注册
     *
     * @param request 注册请求参数
     * @return 新用户 ID
     */
    String register(RegisterRequest request);
}
