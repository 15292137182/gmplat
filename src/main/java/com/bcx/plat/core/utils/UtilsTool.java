package com.bcx.plat.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
     * 判断对象是否有效
     *
     * @param obj 对象
     * @return 返回
     */
    public static boolean isValid(Object obj) {
        return null != obj && !"".equals(obj);
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
            objectMapper = (JacksonAdapter) getBean("jacksonAdapter");
        }
        return objectMapper;
    }

    /**
     * 将字符串从 空白或者标点处 分割，返回为 Set
     * 自带去重功能
     *
     * @param str 字符串
     * @return 返回 list
     */
    public static Set<String> collectToSet(String str) {
        Set<String> result = new HashSet<>();
        if (isValid(str)) {
            String[] ss = str.split("[^a-zA-z0-9\\u4E00-\\u9FA5]+");
            result.addAll(Arrays.asList(ss));
        }
        return result;
    }

    /**
     * 获取 class 内带有某注解的字段名称
     *
     * @param clazz class
     * @param anno  注解 class
     * @return 返回list
     */
    public static List<String> getAnnoFieldName(Class<?> clazz, Class<? extends Annotation> anno) {
        List<String> fs = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        if (null != fields && fields.length != 0) {
            for (Field field : fields) {
                if (field.getAnnotation(anno) != null) {
                    fs.add(field.getName());
                }
            }
        }
        return fs;
    }

    /**
     * 下划线转驼峰
     *
     * @param underline 下划线字符串
     * @param bigCamel  是否大驼峰
     * @return 返回字符串
     */
    public static String underlineToCamel(String underline, boolean bigCamel) {
        StringBuilder sb = new StringBuilder(underline);
        while (sb.indexOf("_") != -1) {
            sb.replace(sb.indexOf("_"), sb.indexOf("_") + 2, (sb.charAt(sb.indexOf("_") + 1) + "").toUpperCase());
        }
        if (bigCamel) {
            sb.replace(0, 1, (sb.charAt(0) + "").toUpperCase());
        } else {
            sb.replace(0, 1, (sb.charAt(0) + "").toLowerCase());
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线方法
     *
     * @param camel 驼峰
     * @return 返回
     */
    public static String camelToUnderline(String camel) {
        StringBuilder sb = new StringBuilder(camel);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) >= 'A' && sb.charAt(i) <= 'Z') {
                // 将该字符替换为下划线规则
                result.append(("_" + sb.charAt(i)).toLowerCase());
            }else{
                result.append(sb.charAt(i));
            }
        }
        return result.toString();
    }
}