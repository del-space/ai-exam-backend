package com.del.edu.aiexambackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true) //开启AOP注解
public class AiExamBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiExamBackendApplication.class, args);
    }

}
