package com.bcx.plat.core.base.support;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

/**
 * javaBean 接口
 * <p>
 * Create By HCL at 2017/8/30
 */
public interface BeanInterface<T extends BeanInterface> extends Serializable {

  /**
   * @return 将 javaBean 的内容输出到 map
   */
  default Map toMap() {
    return jsonToObj(objToJson(this), HashMap.class);
  }

  /**
   * 将 javaBean 中的内容读取到自身,默认根据 setter 方法进行读取
   *
   * @param map 需要读入的 javaBean
   * @return 返回自身
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
            if (Arrays.asList(method.getParameterTypes()[0].getInterfaces()).contains(BeanInterface.class)) {
              Class clazz = method.getParameterTypes()[0];
              BeanInterface bean = (BeanInterface) clazz.newInstance();
              value = bean.fromMap(map);
            } else if (fieldName.equalsIgnoreCase("etc")) {
              Set<String> keys = toMap().keySet();
              Map etc = new HashMap();
              map.forEach((k, v) -> {
                if ("etc".equalsIgnoreCase(k.toString())) {
                  Map temp;
                  if (v instanceof String) {
                    temp = jsonToObj((String) v, Map.class);
                  } else if (v instanceof Map) {
                    temp = (Map) v;
                  } else {
                    temp = jsonToObj(objToJson(v), Map.class);
                  }
                  if (temp != null) {
                    etc.putAll(temp);
                  }
                } else if (!keys.contains(k)) {
                  etc.put(k, v);
                }
              });
              value = etc;
            } else if (value != null) {
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
}
