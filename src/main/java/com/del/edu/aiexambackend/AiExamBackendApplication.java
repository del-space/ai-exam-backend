package com.del.edu.aiexambackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true) //开启AOP注解
@MapperScan("com.del.edu.aiexambackend.mapper")
public class AiExamBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiExamBackendApplication.class, args);
    }

}