package com.bcx.plat.core.base.support;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

/**
 * javaBean 接口
 * <p>
 * Create By HCL at 2017/8/30
 */
public interface BeanInterface<T extends BeanInterface> extends Serializable {

  @JsonIgnore
  List<BeanInterface> getJoinTemplates();

  @JsonIgnore
  default Serializable getPk() {
    return null;
  }

  /**
   * 更多序列化配置
   *
   * @return 将 javaBean 的内容输出到 map
   * @see com.bcx.plat.core.utils.JacksonAdapter
   */
  default Map toMap() {
    return jsonToObj(objToJson(this), HashMap.class);
  }

  /**
   * 将 javaBean 中的内容读取到自身,默认根据 setter 方法进行读取
   * 处理方式 : 如果一个 Map 中的某字段在所有类型的字段，例如扩展字段、基础字段、公共字段中都能找到，则所有该字段均会被赋值
   * <p>
   * 更多反序列化配置：
   *
   * @param map 需要读入的 javaBean
   * @return 返回自身
   * @see com.bcx.plat.core.utils.JacksonAdapter
   */
  @SuppressWarnings("unchecked")
  default T fromMap(Map map) {
    Class current = getClass();
    while (current != Object.class) {
      Method[] methods = current.getDeclaredMethods();
      for (Method method : methods) {
        if (method.getName().startsWith("set")
            && method.getAnnotationsByType(JsonIgnore.class).length == 0
            && method.getParameterCount() == 1) {
          String _fieldName = method.getName().substring(3, method.getName().length());
          String fieldName = _fieldName.substring(0, 1).toLowerCase() + _fieldName.substring(1, _fieldName.length());
          Object value = map.get(fieldName);
          try {
            if (value != null) {
              if (method.getParameterTypes()[0] != value.getClass()) {
                if (value instanceof String) {
                  value = jsonToObj((String) value, value.getClass());
                } else {
                  value = jsonToObj(objToJson(value), method.getParameterTypes()[0]);
                }
              }
            }
            if (value != null) method.invoke(this, value);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      current = current.getSuperclass();
    }
    return (T) this;
  }

  default Map<String, Object> toDbMap() {
    return toMap();
  }

  default T fromDbMap(Map map) {
    return fromMap(map);
  }

  ;
}
