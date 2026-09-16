package com.del.edu.aiexambackend.service.base.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.common.utils.security.BCryptUtil;
import com.del.edu.aiexambackend.common.utils.security.JwtUtil;
import com.del.edu.aiexambackend.common.utils.result.ThrowUtils;
import com.del.edu.aiexambackend.exception.BusinessException;
import com.del.edu.aiexambackend.exception.ErrorCode;
import com.del.edu.aiexambackend.mapper.UserMapper;
import com.del.edu.aiexambackend.model.dto.LoginResponse;
import com.del.edu.aiexambackend.model.dto.RegisterRequest;
import com.del.edu.aiexambackend.model.entity.Tenant;
import com.del.edu.aiexambackend.model.entity.User;
import com.del.edu.aiexambackend.service.base.TenantService;
import com.del.edu.aiexambackend.service.base.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户服务实现
 *
 * @author: Del
 * @date: 2026-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /** 默认注册角色：学生 */
    private static final String DEFAULT_ROLE = "STUDENT";

    @Resource
    private TenantService tenantService;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(String tenantCode, String username, String password, String ip) {
        // 1. 基础参数校验
        ThrowUtils.throwIf(StrUtil.isBlank(tenantCode), ErrorCode.PARAM_ERROR, "租户编码不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(username), ErrorCode.PARAM_ERROR, "用户名不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(password), ErrorCode.PARAM_ERROR, "密码不能为空");

        // 2. 校验租户可用性（含禁用/过期判断）
        Tenant tenant = getValidTenant(tenantCode);

        // 3. 校验用户状态
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getTenantId, tenant.getId())
                .eq(User::getUsername, username));
        ThrowUtils.throwIf(user == null, ErrorCode.USER_PASSWORD_ERROR);
        ThrowUtils.throwIf(Boolean.FALSE.equals(user.getEnabled()), ErrorCode.USER_DISABLED);

        // 4. 校验密码
        ThrowUtils.throwIf(!BCryptUtil.checkPassword(password, user.getPassword()),
                ErrorCode.USER_PASSWORD_ERROR);

        // 5. 更新最后登录信息
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ip);
        updateById(user);

        // 6. 生成 JWT 并组装响应
        String token = jwtUtil.generateToken(user.getId(), user.getTenantId());
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setExpiresAt(System.currentTimeMillis() + jwtUtil.getExpireMillis());
        response.setUserInfo(toUserInfo(user));
        return response;
    }

    @Override
    public String register(RegisterRequest req) {
        // 1. 参数校验
        ThrowUtils.throwIf(StrUtil.isBlank(req.getUsername()), ErrorCode.PARAM_ERROR, "用户名不能为空");
        ThrowUtils.throwIf(!req.getUsername().matches("^[a-zA-Z][a-zA-Z0-9]{7,12}$"),
                ErrorCode.PARAM_ERROR, "用户名需以字母开头，8-13位，仅支持字母和数字");
        ThrowUtils.throwIf(StrUtil.isBlank(req.getPassword()), ErrorCode.PARAM_ERROR, "密码不能为空");
        ThrowUtils.throwIf(req.getPassword().length() < 6 || req.getPassword().length() > 11,
                ErrorCode.PARAM_ERROR, "密码长度需在 6-11 位之间");
        ThrowUtils.throwIf(StrUtil.isBlank(req.getTenantCode()), ErrorCode.PARAM_ERROR, "租户编码不能为空");

        // 2. 校验租户可用性
        Tenant tenant = getValidTenant(req.getTenantCode());

        // 3. 同租户下用户名查重
        long exists = count(new LambdaQueryWrapper<User>()
                .eq(User::getTenantId, tenant.getId())
                .eq(User::getUsername, req.getUsername()));
        ThrowUtils.throwIf(exists > 0, ErrorCode.USER_ALREADY_EXISTS);

        // 4. 构建并保存用户
        User user = new User();
        user.setTenantId(tenant.getId());
        user.setUsername(req.getUsername());
        user.setPassword(BCryptUtil.hashPassword(req.getPassword()));
        user.setRole(DEFAULT_ROLE);
        user.setEnabled(true);
        save(user);
        return user.getId();
    }

    // ============================================================
    // 私有方法：租户校验
    // ============================================================

    /**
     * 根据租户编码获取可用租户（自动校验存在性、启用状态、过期时间）
     */
    private Tenant getValidTenant(String tenantCode) {
        Tenant tenant = tenantService.getOne(
                new LambdaQueryWrapper<Tenant>().eq(Tenant::getCode, tenantCode)
        );
        ThrowUtils.throwIf(tenant == null, ErrorCode.TENANT_NOT_FOUND);
        ThrowUtils.throwIf(tenant.getStatus() == null || tenant.getStatus() != 1,
                ErrorCode.TENANT_DISABLED);
        if (tenant.getExpireAt() != null && tenant.getExpireAt().before(new Date())) {
            throw new BusinessException(ErrorCode.TENANT_EXPIRED);
        }
        return tenant;
    }

    // ============================================================
    // 私有方法：实体转换
    // ============================================================

    /**
     * User 实体 -> LoginResponse.UserInfo（脱敏后返回给前端）
     */
    private LoginResponse.UserInfo toUserInfo(User user) {
        LoginResponse.UserInfo info = new LoginResponse.UserInfo();
        info.setId(user.getId());
        info.setTenantId(user.getTenantId());
        info.setUsername(user.getUsername());
        info.setRealName(user.getRealName());
        info.setEmail(user.getEmail());
        info.setPhone(user.getPhone());
        info.setRole(user.getRole());
        return info;
    }
}
