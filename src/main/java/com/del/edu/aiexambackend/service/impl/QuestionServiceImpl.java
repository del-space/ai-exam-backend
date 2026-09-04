package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.Question;
import com.del.edu.aiexambackend.service.QuestionService;
import com.del.edu.aiexambackend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【question(题目表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




