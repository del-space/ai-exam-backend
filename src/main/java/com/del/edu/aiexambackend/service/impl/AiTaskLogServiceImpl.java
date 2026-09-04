package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.AiTaskLog;
import com.del.edu.aiexambackend.service.AiTaskLogService;
import com.del.edu.aiexambackend.mapper.AiTaskLogMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【ai_task_log(AI调用日志表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class AiTaskLogServiceImpl extends ServiceImpl<AiTaskLogMapper, AiTaskLog>
    implements AiTaskLogService{

}




