package com.del.edu.aiexambackend.config;


import com.del.edu.aiexambackend.web.interceptor.TenantInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于注册拦截器
 *
 * @author: Del
 * @date: 2026-09
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注入租户拦截器
     * Resource注解会从容器中查找名为tenantInterceptor的Bean并注入
     */
    @Resource
    private TenantInterceptor tenantInterceptor;

    /**
     * 配置拦截器
     * @paramInterceptorRegistry 拦截器注册器，用于注册各种拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册租户拦截器
        registry.addInterceptor(tenantInterceptor)
                // 拦截所有/api/路径下的请求
                .addPathPatterns("/api/**")
                // 排除登录和注册接口，不进行拦截
                .excludePathPatterns(
                        "/api/auth/login",
                        "/api/auth/register"
                );
    }
}
