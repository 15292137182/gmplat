package com.bcx.plat.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.bcx.plat.core.utils.SpringContextHolder.getBean;

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
            objectMapper = (LongToStringAdapter) getBean("longToStringAdapter");
        }
        return objectMapper;
    }
}