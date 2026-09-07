package com.del.edu.aiexambackend.config.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.del.edu.aiexambackend.common.utils.SnowflakeIdGenerator;
import jakarta.annotation.Resource;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * mybatis-plus 自动填充
 *
 * @author: Del
 * @date: 2026-09
 */
@Configuration
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public void insertFill(MetaObject metaObject) {
        // 主建自动填充
        this.strictInsertFill(
            metaObject,
            "id",
            String.class,
            String.valueOf(snowflakeIdGenerator.nextId())
        );

        // 公共字段填充
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "editTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(
                metaObject,
                "updateTime",
                LocalDateTime.class,
                LocalDateTime.now()
        );
    }
}
