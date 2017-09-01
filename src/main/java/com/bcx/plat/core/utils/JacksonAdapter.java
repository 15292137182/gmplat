package com.bcx.plat.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 处理:
 * <p>
 * 1、springMVC 在处理数字类型的时候造成的精度丢失问题
 * 2、在反序列化时，如果字段不匹配或者不能识别，则跳过该字段继续进行反序列化
 */
public class JacksonAdapter extends ObjectMapper {

  public JacksonAdapter() {
    super();
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    registerModule(simpleModule);
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  /**
   * 创建类型，如果你想将数据反序列化含有类型，虽然第二个参数是数组，但是不传改方法会抛出异常
   * <p>
   * 1、List<User>: 构造类型
   * createType(List.class, User.class)
   * <p>
   * 2、Mao<String, User>：构造类型
   * createType(Map.class, String.class, User.class)
   *
   * @param collectionClass 集合类型
   * @param elementClasses  集合内的类型
   * @return 返回类型定义
   */
  public JavaType createType(Class<?> collectionClass, Class<?>... elementClasses) {
    return getTypeFactory().constructParametricType(collectionClass, elementClasses);
  }

}
