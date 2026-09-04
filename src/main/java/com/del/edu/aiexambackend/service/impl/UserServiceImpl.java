package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.User;
import com.del.edu.aiexambackend.service.UserService;
import com.del.edu.aiexambackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




