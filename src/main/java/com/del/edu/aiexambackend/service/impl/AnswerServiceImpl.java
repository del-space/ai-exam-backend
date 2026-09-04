package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.Answer;
import com.del.edu.aiexambackend.service.AnswerService;
import com.del.edu.aiexambackend.mapper.AnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【answer(答题详情表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
    implements AnswerService{

}




