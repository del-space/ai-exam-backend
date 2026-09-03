package com.del.edu.aiexambackend.common.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

    /**
     * 对原始密码进行加密，返回加密后的字符串（包含 salt）
     *
     * @param plainPassword 明文密码
     * @return 加密后的密码
     */
    public static String hashPassword(String plainPassword) {
        // 这里的 gensalt() 默认强度是 10，也可以手动指定
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * 对原始密码进行验证
     *
     * 校验用户输入的明文密码与数据库中存储的加密密码是否匹配
     * @param plainPassword 明文密码
     * @param hashedPassword 密文密码
     * @return 验证成功返回 true，验证失败返回 false
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * 对原始密码进行加密，返回加密后的字符串（包含 salt）
     *
     * @param plainPassword 明文密码
     * @param strength 强度，值越小，加密速度越快，安全性越低
     * @return 加密后的密码
     */
    public static String hashPassword(String plainPassword, int strength) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(strength));
    }
}
