package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.Class;
import com.del.edu.aiexambackend.service.ClassService;
import com.del.edu.aiexambackend.mapper.ClassMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【class(班级表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class>
    implements ClassService{

}




