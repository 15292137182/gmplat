package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.base.template.BaseTemplateBean;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Method;
import java.util.*;

import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 基础 entity 类，建议所有实体类均继承此类
 * <p>
 * Create By HCL at 2017/7/31
 */
public class BaseModel<T extends BaseModel> extends BaseORM<T> implements BeanInterface<T> {

  @JsonIgnore
  private BaseTemplateBean templateBean = new BaseTemplateBean();
  @JsonIgnore
  private Map etc;
  private String rowId;

  /**
   * 构建删除信息
   *
   * @return 返回自身
   */
  @SuppressWarnings("unchecked")
  public T buildCreateInfo() {
    templateBean.buildCreateInfo();
    setRowId(lengthUUID(32));
    return (T) this;
  }

  public String getRowId() {
    return this.rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public BaseTemplateBean getBaseTemplateBean() {
    return templateBean;
  }

  public void setBaseTemplateBean(BaseTemplateBean templateBean) {
    this.templateBean = templateBean;
  }

  /**
   * @return 返回加入的模版
   */
  @Override
  public List<BeanInterface> getJoinTemplates() {
    return Collections.singletonList(getBaseTemplateBean());
  }

  /**
   * 将 entity 实体类转换为 map
   *
   * @return map
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> toMap() {
    Map map = jsonToObj(objToJson(this), HashMap.class);
    assert map != null;
    if (this.etc != null) {
      map.putAll(etc);
    }
    if (getJoinTemplates() != null) {
      for (BeanInterface bean : getJoinTemplates()) {
        if (null != bean) {
          map.putAll(bean.toMap());
        }
      }
    }
    return map;
  }


  /**
   * 尝试从 map 中读取 entity 类
   * <p>
   * 为了满足需求，我决定造一个轮子
   *
   * @param map map数据
   * @return 返回实体类
   */
  @Override
  @SuppressWarnings("unchecked")
  public T fromMap(Map map) {
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
              // 将数据读入 javaBean
              if (null != getJoinTemplates()) {
                for (BeanInterface bean : getJoinTemplates()) {
                  if (null != bean) {
                    bean.fromMap(map);
                  }
                }
              }
              map.forEach((k, v) -> {
                if ("etc".equalsIgnoreCase(k.toString())) {
                  // 处理扩属属性字段
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

  private MoreBatis getMoreBatis() {
    return (MoreBatis) SpringContextHolder.getBean("moreBatis");
  }

  public Map getEtc() {
    return etc;
  }

  public void setEtc(Map etc) {
    this.etc = etc;
  }
}
