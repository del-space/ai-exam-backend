package com.del.edu.aiexambackend.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.del.edu.aiexambackend.model.entity.Tenant;
import com.del.edu.aiexambackend.service.TenantService;
import com.del.edu.aiexambackend.mapper.TenantMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【tenant(租户/机构表)】的数据库操作Service实现
* @createDate 2026-09-04 10:21:53
*/
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
    implements TenantService{

}




