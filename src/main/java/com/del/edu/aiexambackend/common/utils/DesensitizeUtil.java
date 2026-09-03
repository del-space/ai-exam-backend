package com.del.edu.aiexambackend.common.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 数据脱敏工具类
 * 用于对敏感信息进行脱敏处理
 */
public class DesensitizeUtil {

    /**
     * 手机号脱敏
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public static String desensitizePhone(String phone) {
        if (StrUtil.isBlank( phone) || phone.length() != 11) {
            return phone;
        }

        // 保留 3位 和 后4位，中间使用 * 隐藏
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 邮箱脱敏
     * @param email 邮箱
     * @return 脱敏后的邮箱
     */
    public static String desensitizeEmail(String email) {
        if (StrUtil.isBlank(email) || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        // 如果用户名长度 <=2，只保留第一个字符
        if (username.length() <= 2) {
            return username.charAt(0) + "***@" + domain;
        }

        // 保留用户名第一个字符，其余用 * 隐藏
        return username.charAt(0) + "***" + "@" + domain;
    }

    /**
     * 判断手机号是否已脱敏
     * @param phone 手机号
     * @return 是否已脱敏
     */
    public static boolean isDesensitizedPhone(String phone) {
        return phone != null && phone.contains("****");
    }

    /**
     * 判断邮箱是否已脱敏
     * @param email 邮箱
     * @return 是否已脱敏
     */
    public static boolean isDesensitizedEmail(String email) {
        return email != null && email.contains("***@");
    }
}
