package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.Exam;
import com.del.edu.aiexambackend.service.ExamService;
import com.del.edu.aiexambackend.mapper.ExamMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【exam(考试表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam>
    implements ExamService{

}




