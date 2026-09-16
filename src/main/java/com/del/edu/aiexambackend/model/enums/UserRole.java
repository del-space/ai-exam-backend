package com.del.edu.aiexambackend.model.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户角色枚举
 *  SUPER_ADMIN / ORG_ADMIN / TEACHER / STUDENT
 *
 * @author: Del
 * @date: 2026-09
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    SUPER_ADMIN("SUPER_ADMIN", "超级管理员"),
    ORG_ADMIN("ORG_ADMIN", "机构管理员"),
    TEACHER("TEACHER", "教师"),
    STUDENT("STUDENT", "学生");

    /** 角色编码（存 DB / API 传输） */
    @EnumValue
    @JsonValue
    private final String code;

    /** 中文描述（仅 API 响应中暴露） */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final String desc;

    @JsonCreator
    public static UserRole of(String code) {
        return Arrays.stream(values())
                .filter(r -> r.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未知角色码: " + code));
    }
}
