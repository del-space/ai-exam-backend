package com.del.edu.aiexambackend.common.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author: Del
 * @date: 2026-09
 */
@Component
public class JwtUtil {

    /**
     * 生成 JWT 令牌
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * JWT 令牌过期时间
     */
    @Value("${jwt.expire}")
    private long expire;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT令牌的方法
     * @param userId 用户ID
     * @param tenantId 租户ID
     * @return 返回生成的JWT令牌字符串
     */
    public String generateToken(String userId, String tenantId) {
        // 使用Jwts构建器创建JWT令牌
        return Jwts.builder()
                // 设置令牌的主题为用户ID
                .setSubject(userId)
                // 添加租户ID作为声明
                .claim("tenantId", tenantId)
                // 设置令牌的签发时间为当前时间
                .setIssuedAt(new Date())
                // 设置令牌的过期时间，当前时间加上过期时长(EXPIRE)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                // 使用密钥(KEY)对令牌进行签名
                .signWith(getKey())
                // 压缩生成JWT令牌字符串
                .compact();
    }

    /**
     * 统一解析JWT（只解析一次，返回完整Claims）
     *
     * @param token JWT令牌
     * @return Claims对象，包含 userId 和 tenantId
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从JWT令牌中解析用户ID
     *
     * @param token JWT令牌字符串
     * @return 用户ID（令牌中的subject）
     */
    public String parseUserId(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 从JWT令牌中解析租户ID
     *
     * @param token JWT令牌字符串
     * @return 解析出的租户ID字符串
     */
    public String parseTenantId(String token) {
        // 使用JWT解析器构建器创建解析器实例
        return parseToken(token).get("tenantId", String.class);
    }

    /**
     * 暴露 JWT 过期时长（毫秒），供业务层计算 token 过期时间戳
     *
     * @return 过期时长（毫秒）
     */
    public long getExpireMillis() {
        return expire;
    }
}