package com.del.edu.aiexambackend.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Del
 * @date: 2026-03-25
 * @description: 跨域配置类
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有的请求
        registry.addMapping("/**")
                // 放行哪些域名
                .allowedOriginPatterns(getAllowedOrigins())
                // 允许的请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许发送 Cookie
                .allowCredentials(true)
                // 允许的请求头
                .allowedHeaders("*")
                // 暴露的请求头
                .exposedHeaders("*")
                // 允许的响应时间
                .maxAge(3600);
    }

    private String[] getAllowedOrigins() {
        // 返回允许的域名列表
        return new String[]{"http://localhost:4596", "http://127.0.0.1:4596"};
    }
}
