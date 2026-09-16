package com.del.edu.aiexambackend.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.del.edu.aiexambackend.config.handler.MyTenantLineHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Del
 * @date: 2026-09
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置Mybatis-Plus的插件，添加多租户拦截器
     * @param tenantLineHandler 多租户处理器，用于处理多租户的 SQL解析
     * @return MybatisPlusInterceptor 配置好的 Mybatis-Plus 拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MyTenantLineHandler tenantLineHandler) {
        // 创建MybatisPlusInterceptor实例
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
         // 向拦截器中添加多租户内部拦截器，传入租户处理器
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler));
        // 返回配置好的拦截器
        return interceptor;
    }
}
