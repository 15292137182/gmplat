package com.bcx.plat.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.bcx.plat.core.utils.SpringContextHolder.getBean;
import static java.time.LocalDateTime.now;

/**
 * 基本工具类
 * Created by hcl at 2017/07/28
 */
public class UtilsTool {

    private static ObjectMapper objectMapper;

    /**
     * 禁止使用 new 的方法构造该类
     */
    private UtilsTool() {
    }

    /**
     * 返回指定长度的随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String lengthUUID(int length) {
        StringBuilder sb = new StringBuilder();
        while (length != sb.length()) {
            sb.append(UUID.randomUUID());
            if (length < sb.length()) {
                sb.delete(length - 1, sb.length() - 1);
            }
        }
        return sb.toString();
    }

    /**
     * 获取长度为 10 的日期字符串
     *
     * @return 日期字符串
     */
    public static String getDateBy10() {
        return getDateTimeNow("yyyy-MM-dd");
    }

    /**
     * 获取当前事件日期
     *
     * @return 返回时间日期
     */
    public static String getDateTimeNow() {
        return getDateTimeNow("yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 根据指定时间格式来格式化日期
     *
     * @param pattern 事件格式
     * @return 返回时间格式的字符串
     */
    public static String getDateTimeNow(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return now().format(formatter);
    }

    /**
     * 对象转Json字符串
     *
     * @param obj 对象
     * @return 返回字符串
     */
    public static String objToJson(Object obj) {
        try {
            return initMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json 转换为 Object
     *
     * @param json json 字符串
     * @param <T>  类型
     * @return 返回类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObj(String json, Class<T> clazz) {
        try {
            return initMapper().readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 ObjectMapper
     *
     * @return 返回 ObjectMapper
     */
    private static ObjectMapper initMapper() {
        if (null == objectMapper) {
            objectMapper = (JacksonAdapter) getBean("longToStringAdapter");
        }
        return objectMapper;
    }
}