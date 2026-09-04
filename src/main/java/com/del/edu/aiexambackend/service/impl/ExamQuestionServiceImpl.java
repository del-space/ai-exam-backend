package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.ExamQuestion;
import com.del.edu.aiexambackend.service.ExamQuestionService;
import com.del.edu.aiexambackend.mapper.ExamQuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【exam_question(考试题目关联表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class ExamQuestionServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion>
    implements ExamQuestionService{

}




