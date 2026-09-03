package com.del.edu.aiexambackend.common.utils;


/**
 * @author: Del
 * @date: 2026-03-30
 * @description: 对象判空工具类
 */
public class ObjectUtils {

    /**
     * 判断对象是否为空，为空则返回默认值
     *
     * @param obj 要判断的对象
     * @param defaultValue 默认值
     * @param <T> 对象类型
     * @return 如果对象不为空返回对象本身，否则返回默认值
     */
    public static <T> T defaultIfEmpty(T obj, T defaultValue) {
        return (obj != null) ? obj : defaultValue;
    }

    /**
     * 判断条件是否为真，为真则返回第一个值，否则返回第二个值
     *
     * @param condition 判断条件
     * @param trueValue 条件为真时返回的值
     * @param falseValue 条件为假时返回的值
     * @param <T> 返回值类型
     * @return 根据条件返回对应的值
     */
    public static <T> T ifTrue(boolean condition, T trueValue, T falseValue) {
        return condition ? trueValue : falseValue;
    }

    /**
     * 判断对象是否为空，为空则返回指定值，否则返回对象本身
     *
     * @param obj 要判断的对象
     * @param returnValue 对象为空时返回的值
     * @param <T> 对象和返回值类型
     * @return 如果对象为空返回指定值，否则返回对象本身
     */
    public static <T> T returnIfNull(T obj, T returnValue) {
        return (obj == null) ? returnValue : obj;
    }


    /**
     * 判断对象是否不为空，不为空则返回指定值，否则返回 null
     *
     * @param obj 要判断的对象
     * @param returnValue 对象不为空时返回的值
     * @param <T> 返回值类型
     * @return 如果对象不为空返回指定值，否则返回 null
     */
    public static <T> T returnIfNotNull(T obj, T returnValue) {
        return (obj != null) ? returnValue : null;
    }

    /**
     * 判断条件是否为真，为真则返回第一个值，否则返回 null
     *
     * @param condition 判断条件
     * @param value 条件为真时返回的值
     * @param <T> 返回值类型
     * @return 条件为真返回值，否则返回 null
     */
    public static <T> T returnIfTrue(boolean condition, T value) {
        return condition ? value : null;
    }

    /**
     * 判断条件是否为假，为假则返回第一个值，否则返回 null
     *
     * @param condition 判断条件
     * @param value 条件为假时返回的值
     * @param <T> 返回值类型
     * @return 条件为假返回值，否则返回 null
     */
    public static <T> T returnIfFalse(boolean condition, T value) {
        return condition ? null : value;
    }
}
