package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.ClassStudent;
import com.del.edu.aiexambackend.service.ClassStudentService;
import com.del.edu.aiexambackend.mapper.ClassStudentMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【class_student(学生班级关联表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class ClassStudentServiceImpl extends ServiceImpl<ClassStudentMapper, ClassStudent>
    implements ClassStudentService{

}




