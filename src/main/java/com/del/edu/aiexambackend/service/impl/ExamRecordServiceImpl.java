package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.ExamRecord;
import com.del.edu.aiexambackend.service.ExamRecordService;
import com.del.edu.aiexambackend.mapper.ExamRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【exam_record(考试记录表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class ExamRecordServiceImpl extends ServiceImpl<ExamRecordMapper, ExamRecord>
    implements ExamRecordService{

}




