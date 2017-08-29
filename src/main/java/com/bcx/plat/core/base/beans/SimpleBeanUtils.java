package com.bcx.plat.core.base.beans;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

/**
 * 简单 Bean 的工具类
 * <p>
 * Create By HCL at 2017/8/29
 */
public abstract class SimpleBeanUtils<T extends SimpleBeanUtils> implements BeanLimit<T> {

  /**
   * 将当前对象转换为 Map
   *
   * @return 返回转换后的 map
   */
  public Map toMap() {
    return jsonToObj(objToJson(this), HashMap.class);
  }

  /**
   * 将 map 的值读取到 javaBean 中
   *
   * @param map map
   * @return 返回自身
   */
  @SuppressWarnings("unchecked")
  public T fromMap(Map map) {
    Class current = getClass();
    while (current != SimpleBeanUtils.class) {
      Method[] methods = current.getDeclaredMethods();
      for (Method method : methods) {
        if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
          String _fieldName = method.getName().substring(3, method.getName().length());
          String fieldName = _fieldName.substring(0, 1).toLowerCase() + _fieldName.substring(1, _fieldName.length());
          Object value = map.get(fieldName);
          if (value != null) {
            try {
              if (method.getParameterTypes()[0] != value.getClass()) {
                value = jsonToObj(objToJson(value), value.getClass());
              }
              method.invoke(this, value);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
      current = current.getSuperclass();
    }
    return (T) this;
  }
}