package com.del.edu.aiexambackend.common.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    /**
     * 雪花算法起始时间戳常量
     *
     * 这个时间戳作为雪花算法的时间基准点，用于计算相对时间戳
     * 时间戳值对应2021-01-01 00:00:00 UTC
     *
     * 注意：一旦确定后不能修改，否则会导致生成的ID不唯一
     */
    private final static long START_TIMESTAMP = 1609459200000L;

    /**
     * 序列号位数常量，用于雪花算法中序列号部分的位数定义
     * 该值决定了在同一毫秒内可以生成的序列号数量上限为2^12 = 4096个
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识位数
     *
     * 定义雪花算法中机器标识部分占用的位数为10位，
     * 用于区分不同机器或节点生成的ID
     */
    private final static long MACHINE_BIT = 10;


    /**
     * 最大序列号值
     * 根据序列号位数计算得出，用于序列号的循环使用
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);


    /**
     * 机器ID左移位数
     * 用于将机器ID移动到正确的位置，为后续的ID组合做准备
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;


    /**
     * 时间戳左移位数
     * 用于将时间戳移动到最高位，确保生成的ID具有时间序
     */
    private final static long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;


    /**
     * 机器标识符
     * 用于区分不同的机器节点，确保不同机器生成的ID不会冲突
     */
    private long machineId;


    /**
     * 序列号
     * 在同一毫秒内生成多个ID时使用的计数器，从0开始递增
     */
    private long sequence = 0L;


    /**
     * 上次生成ID的时间戳
     * 用于判断是否需要等待下一毫秒或重置序列号
     */
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(@Value("${machine.id:1}") long machineId) {
        if (machineId > ~(-1L << MACHINE_BIT)) {
            throw new IllegalArgumentException("machineId can't be greater than " + ~(-1L << MACHINE_BIT));
        }
    }

    /**
     * 生成下一个ID的方法
     * @return 生成的唯一ID
     */
    public synchronized long nextId() {
        // 1. 获取当前时间的时间戳
        long timestamp = System.currentTimeMillis();

        // 2. 时钟回拨处理
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp));
        }

        // 3.判断是否是同一毫秒内生成ID
        if (timestamp == lastTimestamp) {
            // 4.是同一毫秒内生成ID
            sequence = (sequence + 1) & MAX_SEQUENCE;

            // 5. 序列化用完，需要等待下一毫秒
            if (sequence == 0) {
                timestamp = getNextTimestamp();
            }
        } else {
            // 6. 不是同一毫秒内生成ID
            sequence = 0;
        }

        // 7. 保存当前时间戳，用于下一次生成ID
        lastTimestamp = timestamp;

        // 8. 组装ID
        return (timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }


    /**
     * 获取下一个时间戳
     * 该方法用于获取一个大于上一次时间戳的新时间戳
     * 如果当前时间戳小于或等于上一次时间戳，则循环获取新的时间戳
     *
     * @return 返回一个大于lastTimestamp的时间戳
     */
    private long getNextTimestamp() {
        // 1. 获取当前系统时间戳
        long timestamp = System.currentTimeMillis();

        // 2. 循环直到获取到比 lastTimestamp 更大的时间戳
        while (timestamp <= lastTimestamp) {
            // 3. 重新获取当前系统时间戳
            timestamp = System.currentTimeMillis();
        }

        // 4. 获取获取到的新时间戳
        return timestamp;
    }
}
