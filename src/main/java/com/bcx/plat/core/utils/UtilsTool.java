package com.bcx.plat.core.utils;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static com.bcx.plat.core.utils.SpringContextHolder.getBean;
import static java.time.LocalDateTime.now;

/**
 * 基本工具类 Created by hcl at 2017/07/28
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
    return null != obj && !"".equals(obj.toString());
  }

  public static boolean isValid(List list) {
    return null != list && list.size() > 0;
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
    return getDateTimeNow("yyyy-MM-dd HH:mm:ss");
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
   * @param json json
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
   * 将字符串从 指定字符 分割，返回为 Set 自带去重功能
   * <p>
   * 自指定字符包括： 空白，（圆角和半角）逗号，句号
   *
   * @param str 字符串
   * @return 返回 list
   */
  public static Set<String> collectToSet(String str) {
    Set<String> result = new HashSet<>();
    if (isValid(str)) {
      String[] ss = str.split("[\\s;,；，]+");
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
    List<String> fs = new LinkedList<>();
    Field[] fields = clazz.getDeclaredFields();
    if (null != fields && fields.length != 0) {
      for (Field field : fields) {
        if (field.getAnnotation(anno) != null) {
          fs.add(field.getName());
        }
      }
    }
    if (fs.isEmpty()) {
      throw new NullPointerException("检查一下实体类是不是没有加@TablePK注解");
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
      sb.replace(sb.indexOf("_"), sb.indexOf("_") + 2,
              (sb.charAt(sb.indexOf("_") + 1) + "").toUpperCase());
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
      } else {
        result.append(sb.charAt(i));
      }
    }
    return result.toString();
  }

  /**
   * 将map中的键值对转换为一组and条件
   *
   * @param args
   * @return
   */
  public static And convertMapToFieldConditions(Map<String, Object> args) {
    return new And(args.entrySet().stream().map((entry) -> {
      final Object value = entry.getValue();
      if (value instanceof Collection) {
        return new FieldCondition(entry.getKey(), Operator.IN, value);
      } else {
        return new FieldCondition(entry.getKey(), Operator.EQUAL, value);
      }
    }).collect(Collectors.toList()));
  }

  /**
   * 创建空格查询的查询条件
   *
   * @param columns 列
   * @param values  关键字
   * @return
   */
  public static Or createBlankQuery(Collection<String> columns, Collection<String> values) {
    List<Condition> conditions = new LinkedList<>();
    for (String column : columns) {
      for (String value : values) {
        conditions.add(new FieldCondition(column, Operator.LIKE_FULL, value));
      }
    }
    return new Or(conditions);
  }

  /**
   * 将下划线风格key的map转换为驼峰法则key的map
   *
   * @param origin 输入的PageResult
   * @return
   */
  public static PageResult<Map<String, Object>> underlineKeyMapListToCamel(PageResult<Map<String, Object>> origin) {
    origin.setResult(underlineKeyMapListToCamel(origin.getResult()));
    return origin;
  }

  /**
   * 将下划线风格key的map转换为驼峰法则key的map
   *
   * @param origin 输入MapList
   * @return
   */
  static public List<Map<String, Object>> underlineKeyMapListToCamel(List<Map<String, Object>> origin) {
    return origin.stream().map((row) -> {
      HashMap<String, Object> out = new HashMap<>();
      for (Entry<String, Object> entry : row.entrySet()) {
        out.put(UtilsTool.underlineToCamel(entry.getKey(), false), entry.getValue());
      }
      return out;
    }).collect(Collectors.toList());
  }

  static public And excludeDeleted(Condition condition) {
    return new And(new Or(new FieldCondition("delete_flag", Operator.EQUAL, BaseConstants.NOT_DELETE_FLAG),
            new FieldCondition("delete_flag", Operator.IS_NULL, null)), condition);
  }

  /**
   * 获取属性代码字段信息
   *
   * @param key 接受properties可以对应的key
   * @return 返回properies中value值
   */
  static public String loadProperties(String key) {
    Properties properties = new Properties();
    InputStream resourceAsStream = UtilsTool.class.getClassLoader()
            .getResourceAsStream("properties/sequence.properties");
    try {
      properties.load(resourceAsStream);
      return properties.getProperty(key);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (resourceAsStream != null) {
        try {
          resourceAsStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  /**
   * 数据排序
   * "{\"str\":\"rowId\", \"num\":1}"
   *
   * @param order 接受两个参数 str为对应表字段信息  num对应为__1_为正序  __0__为倒序
   * @return linkedList参数
   */
  static public LinkedList<Order> dataSort(String order) {
    LinkedList<Order> orders = new LinkedList<>();
    if (order == null) {
      return orders;
    }
    HashMap hashMap = UtilsTool.jsonToObj(order, HashMap.class);
    String str = (String) hashMap.get("str");
    int num = Integer.parseInt(hashMap.get("num").toString());
    String s = UtilsTool.camelToUnderline(str);
    orders.add(new Order(new com.bcx.plat.core.morebatis.component.Field(s), num));
    return orders;
  }


  /**
   * 版本号叠加升级
   *
   * @param version 接受版本号
   * @return
   */
  public static String upgradeVersion(String version) {
    StringBuffer sb = new StringBuffer();
    boolean findUnm = false;
    String s = version + "_0.1d";
    double sum = 0D;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '.' || (c >= '0' && c <= '9')) {
        sb.append(c);
        findUnm = true;
      } else if (findUnm) {
        try {
          sum += Double.parseDouble(sb.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
        sb = new StringBuffer();
        findUnm = false;
      }
    }
    StringBuilder sf = new StringBuilder();
    String str = sum + "";
    String substring = str.substring(str.indexOf(".") + 1);
    if (substring.equals("0")) {
      sf.append(str).append(".0");
      return sf + "";
    }
    return str + "";
  }
}