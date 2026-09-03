package com.del.edu.aiexambackend.common;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: Del
 * @date: 2026-03-25
 * @description: 分页请求
 */
@Data
public class PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8413008118970615928L;

    /**
     * 当前页码
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = "desc";
}
