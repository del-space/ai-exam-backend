package com.del.edu.aiexambackend.common;


import java.io.Serial;
import java.io.Serializable;

/**
 * @author: Del
 * @date: 2026-03-25
 * @description: 删除请求
 */
public class DeleteRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1969094420055850902L;

    /**
     * id
     */
    private Long id;
}
