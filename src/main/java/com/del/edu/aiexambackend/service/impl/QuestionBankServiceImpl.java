package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.QuestionBank;
import com.del.edu.aiexambackend.service.QuestionBankService;
import com.del.edu.aiexambackend.mapper.QuestionBankMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【question_bank(题库表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
    implements QuestionBankService{

}




