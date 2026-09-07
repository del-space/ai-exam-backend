package com.del.edu.aiexambackend.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author: Del
 * @date: 2026-09
 */
public class JwtUtil {

    private static final String SECRET = "your-secret-key-your-secret-key-your-secret-key";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRE = 1000L * 60 * 60 * 24; // 24小时

    /**
     * 生成JWT令牌的方法
     * @param userId 用户ID
     * @param tenantId 租户ID
     * @return 返回生成的JWT令牌字符串
     */
    public static String generateToken(String userId, String tenantId) {
        // 使用Jwts构建器创建JWT令牌
        return Jwts.builder()
                // 设置令牌的主题为用户ID
                .setSubject(userId)
                // 添加租户ID作为声明
                .claim("tenantId", tenantId)
                // 设置令牌的签发时间为当前时间
                .setIssuedAt(new Date())
                // 设置令牌的过期时间，当前时间加上过期时长(EXPIRE)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                // 使用密钥(KEY)对令牌进行签名
                .signWith(KEY)
                // 压缩生成JWT令牌字符串
                .compact();
    }

    /**
     * 从JWT令牌中解析用户ID
     * @param token JWT令牌字符串
     * @return 用户ID（令牌中的subject）
     */
    public static String parseUserId(String token) {
        // 使用JWT解析器构建器创建解析器
        return Jwts.parserBuilder()
                // 设置签名密钥
                .setSigningKey(KEY)
                 // 构建解析器
                .build()
                 // 解析JWT令牌并获取 claims
                .parseClaimsJws(token)
                // 获取令牌主体部分
                .getBody()
                 // 返回subject，即用户ID
                .getSubject();
    }

    /**
     * 从JWT令牌中解析租户ID
     * @param token JWT令牌字符串
     * @return 解析出的租户ID字符串
     */
    public static String parseTenantId(String token) {
        // 使用JWT解析器构建器创建解析器实例
        return Jwts.parserBuilder()
                 // 设置签名密钥
                .setSigningKey(KEY)
                  // 构建解析器
                .build()
                 // 解析JWT令牌并获取声明
                .parseClaimsJws(token)
                 // 获取声明主体
                .getBody()
                // 从声明中获取名为"tenantId"的值，并将其作为String类型返回
                .get("tenantId", String.class);
    }
}