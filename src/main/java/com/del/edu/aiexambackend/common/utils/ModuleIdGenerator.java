package com.del.edu.aiexambackend.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModuleIdGenerator {

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    /**
     * 根据模块名生成带前缀的唯一ID字符串
     * @param module 模块名称，如 "user", "order", "article"
     * @return 格式如：User_18446744073709551616
     */
    public String generateModuleId(String module) {
        long id = snowflakeIdGenerator.nextId();
        // 统一转大写模块名（首字母大写）
        String prefix = module.substring(0, 1).toUpperCase() + module.substring(1);
        return String.format("%s_%d", prefix, id);
    }

    /**
     * 生成带日期和模块的编码，如 ORDER-20240601-123456789
     * @param module 模块名
     * @return 格式如：ORDER-20240601-1234567890123
     */
    public String  generateFormattedId(String module) {
        long id = snowflakeIdGenerator.nextId();
        String datePrefix = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = module.toUpperCase();
        return String.format("%s-%s-%d", prefix, datePrefix, id);
    }
}
