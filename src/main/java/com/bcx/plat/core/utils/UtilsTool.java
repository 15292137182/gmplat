package com.bcx.plat.core.utils;

import static com.bcx.plat.core.base.BaseConstants.DELETE_FLAG;
import static java.time.LocalDateTime.now;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.AndConditionSequenceBuilder;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.builder.OrderBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 基本工具类 Created by hcl at 2017/07/28
 */
public class UtilsTool {

  private static JacksonAdapter objectMapper;

  private static MoreBatis morebatis;

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

  /**
   * 判断一组对象是否有效
   *
   * @param obj 对象
   * @return 是否有效
   */
  public static boolean isValidAll(Object... obj) {
    for (Object o : obj) {
      if (null != o && !"".equals(o.toString().trim())) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  public static boolean isValid(Collection collection) {
    return null != collection && !collection.isEmpty();
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
   * 获取当前时间日期
   *
   * @return 返回时间日期
   */
  public static String getDateTimeNow() {
    return getDateTimeNow("yyyy-MM-dd HH:mm:ss");
  }


  /**
   * 根据指定时间格式来格式化日期
   *
   * @param pattern 时间格式
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
   * @param <T> 类型
   * @return 返回类型
   */
  @SuppressWarnings("unchecked")
  public static <T> T jsonToObj(String json, Class<T> clazz) {
    if (!isValid(json)) {
      return null;
    }
    try {
      return initMapper().readValue(json, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 反序列化复杂Collection如List<Bean> <p> jsonToObj(json, List.class, Bean.class)
   */
  @SuppressWarnings("unchecked")
  public static <T> T jsonToObj(String json, Class<T> clazz, Class<?>... elements) {
    if (!isValid(json)) {
      return null;
    }
    try {
      return (T) initMapper().readValue(json, initMapper().createType(clazz, elements));
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * 获取 ObjectMapper
   *
   * @return 返回 ObjectMapper
   */
  private static JacksonAdapter initMapper() {
    if (null == objectMapper) {
      objectMapper = new JacksonAdapter();
    }
    return objectMapper;
  }

  /**
   * 将字符串从 指定字符 分割，返回为 Set 自带去重功能 <p> 自指定字符包括： 空白，（圆角和半角）逗号，句号
   *
   * @param str 字符串
   * @return 返回 list
   */
  public static Set<String> collectToSet(String str) {
    Set<String> result = new HashSet<>();
    if (isValid(str)) {
      String[] ss = str.split("[\\s]+");
      result.addAll(Arrays.asList(ss));
    }
    return result;
  }

  /**
   * 获取 class 内带有某注解的字段名称
   *
   * @param clazz class
   * @param anno 注解 class
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
   * @param bigCamel 是否大驼峰
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
   * java 中的 join 函数
   *
   * @param strings 对象数组
   * @param connector 链接符
   * @return 返回处理后的字符串
   */
  public static String join(Object[] strings, Object connector) {
    StringBuilder sb = new StringBuilder();
    for (int _i = 0; _i < strings.length; _i++) {
      sb.append(strings[_i]);
      if (_i != strings.length - 1) {
        sb.append(connector);
      }
    }
    return sb.toString();
  }

  /**
   * map转换为对应的由=组成的and条件
   */
  public static And convertMapToAndCondition(Class<? extends BeanInterface> entityClass,
      Map<String, Object> args) {
    AndConditionSequenceBuilder<ConditionBuilder> conditionBuilder = new ConditionBuilder(
        entityClass).and();
    for (Map.Entry<String, Object> entry : args.entrySet()) {
      final Object value = entry.getValue();
      if (value instanceof Collection) {
        conditionBuilder.in(entry.getKey(), (Collection) value);
      } else {
        conditionBuilder.equal(entry.getKey(), value);
      }
    }
    return (And) conditionBuilder.endAnd().buildDone();
  }

  /**
   * map转换为对应的由like组成的and条件
   */
  public static And convertMapToAndConditionSeparatedByLike(
      Class<? extends BeanInterface> entityClass, Map<String, Object> args) {
    AndConditionSequenceBuilder<ConditionBuilder> conditionBuilder = new ConditionBuilder(
        entityClass).and();
    for (Map.Entry<String, Object> entry : args.entrySet()) {
      final Object value = entry.getValue();
      conditionBuilder.like(entry.getKey(), (String) value);
    }
    return (And) conditionBuilder.endAnd().buildDone();
  }

  /**
   * 创建空格查询的查询条件
   *
   * @param columns 列
   * @param values 关键字
   * @return 返回
   */
  //用Class clz, Collection<String> columns,Collection<String> values的版本替代
  public static Or createBlankQuery(Collection<String> columns, Collection<String> values) {
    List<Condition> conditions = new LinkedList<>();
    columns.forEach(column -> values
        .forEach(value -> conditions.add(new FieldCondition(column, Operator.LIKE_FULL, value))));
    return new Or(conditions);
  }

  /**
   * 创建空格查询的查询条件
   *
   * @param columns 列
   * @param values 关键字
   * @return 返回
   */
  public static Or createBlankQuery(Class clz, Collection<String> columns,Collection<String> values) {
    List<Condition> conditions = new LinkedList<>();
    columns.forEach(column -> values
        .forEach(value -> conditions.add(
            new FieldCondition(getMorebatis().getColumnOrEtcByAlias(clz, column),
                Operator.LIKE_FULL, value))));
    return new Or(conditions);
  }

  /**
   * 创建空格查询的查询条件
   *
   * @param columns 列
   * @param values 关键字
   * @return 返回
   */
  public static Or createBlankQueryByColumns(
      Collection<com.bcx.plat.core.morebatis.component.Field> columns, Collection<String> values) {
    List<Condition> conditions = new LinkedList<>();
    columns.forEach(column -> values
        .forEach(value -> conditions.add(new FieldCondition(column, Operator.LIKE_FULL, value))));
    return new Or(conditions);
  }


  /**
   * 将下划线风格key的map转换为驼峰法则key的map
   *
   * @param origin 输入的PageResult
   */
  public static PageResult<Map<String, Object>> underlineKeyMapListToCamel(
      PageResult<Map<String, Object>> origin) {
    origin.setResult(underlineKeyMapListToCamel(origin.getResult()));
    return origin;
  }

  /**
   * 将下划线风格key的map转换为驼峰法则key的map
   *
   * @param origin 输入MapList
   */
  public static List<Map<String, Object>> underlineKeyMapListToCamel(
      List<Map<String, Object>> origin) {
    return origin.stream().map((row) -> {
      HashMap<String, Object> out = new HashMap<>();
      for (Map.Entry<String, Object> entry : row.entrySet()) {
        out.put(UtilsTool.underlineToCamel(entry.getKey(), false), entry.getValue());
      }
      return out;
    }).collect(Collectors.toList());
  }

  /**
   * 数据排序 "{\"field\":\"rowId\", \"sort\":1}"
   *
   * @param order 接受两个参数 str为对应表字段信息  num对应为__1_为正序  __0__ 为倒序
   * @return linkedList参数
   */
  @SuppressWarnings("unchecked")
  public static LinkedList<Order> dataSort(String order) {
    LinkedList<Order> orders = new LinkedList<>();
    if (order == null) {
      return orders;
    }
    HashMap hashMap = UtilsTool.jsonToObj(order, HashMap.class);
    if (hashMap != null) {
      String str = String.valueOf(hashMap.get("str"));
      int num = Integer.parseInt(hashMap.get("num").toString());
      orders.add(new Order(new com.bcx.plat.core.morebatis.component.Field(str), num));
    }
    return orders;
  }

  /**
   * 数据排序
   *
   * @param entityClass 要排序的类 "{\"str\":\"modifyTime\", \"num\":0}"
   * @param order 接受两个参数 str为对应表字段信息  num对应为__1_为升序  __0__ 为降序
   * @return 排序方式
   */
  public static LinkedList<Order> dataSort(Class<? extends BeanInterface> entityClass,
      String order) {
    LinkedList<Order> orders = new LinkedList<>();
    if (order == null) {
      order = "{\"str\":\"modifyTime\", \"num\":0}"; // 默认按照修改时间排序
    }
    HashMap hashMap = UtilsTool.jsonToObj(order, HashMap.class);
    if (hashMap != null) {
      String str = String.valueOf(hashMap.get("str"));
      int num = Integer.parseInt(hashMap.get("num").toString());
      if (num == 0) {
        orders = new OrderBuilder(entityClass).desc(str).done();
      } else {
        orders = new OrderBuilder(entityClass).asc(str).done();
      }
    }
    return orders;
  }

  /**
   * 时间比较
   *
   * @param oldDate 需要比较的时间
   * @param nowDate 当前时间
   * @return 相差天数
   */
  public static long dateCompare(String oldDate, String nowDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    Date now = null;
    Date old = null;
    long Compare = 0L;
    try {
      now = sdf.parse(nowDate);
      old = sdf.parse(oldDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    if (now != null && old != null) {
      Compare = now.getTime() - old.getTime();
    }
    return Compare / (24 * 60 * 60 * 1000);
  }

  /**
   * 为查询添加通用条件，判断该条数据未被逻辑删除
   *
   * @param condition 查询条件
   * @param entityClass 实体类
   * @return notDelete
   */
  public static Condition addNotDeleteCondition(Condition condition,
      Class<? extends BeanInterface> entityClass) {
    if (null != condition) {
      condition = new ConditionBuilder(entityClass).and().addCondition(condition).or()
          .isNull(entityClass, "etc", "deleteFlag")
          .notEqual(entityClass, "etc", "deleteFlag", DELETE_FLAG).endOr().endAnd().buildDone();
    } else {
      condition = new ConditionBuilder(entityClass).and().or()
          .isNull(entityClass, "etc", "deleteFlag")
          .notEqual(entityClass, "etc", "deleteFlag", DELETE_FLAG).endOr().endAnd().buildDone();
    }
    return condition;
  }

  private static MoreBatis getMorebatis() {
    if (morebatis == null) {
      morebatis = SpringContextHolder.getBean("moreBatis");
    }
    return morebatis;
  }
}