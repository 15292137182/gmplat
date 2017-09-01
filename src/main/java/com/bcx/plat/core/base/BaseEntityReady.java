package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.base.template.BaseTemplateBean;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 基础 entity 类，建议所有实体类均继承此类
 * <p>
 * Create By HCL at 2017/7/31
 */
public class BaseEntityReady<T extends BaseEntityReady> implements BeanInterface<T> {

  @JsonIgnore
  private BaseTemplateBean templateBean = new BaseTemplateBean();

  @JsonIgnore
  private Map etc;

  private String rowId;

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
   * 将 entity 实体类转换为 map
   *
   * @return map
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> toMap() {
    // 压入通用属性
    Map<String, Object> map = this.templateBean.toMap();
    if (null != etc) {
      map.putAll(etc);
    }
    Map temp = jsonToObj(objToJson(this), Map.class);
    if (null != temp) {
      map.putAll(temp);
    }
    return map;
  }


  /**
   * 尝试从 map 中读取 entity 类
   * <p>
   * 为了满足需求，我决定造一个轮子
   *
   * @param map         map数据
   * @param isUnderline 传入map的key是否为下划线命名
   * @return 返回实体类
   */
  @SuppressWarnings("unchecked")
  public T fromMap(Map<String, Object> map, boolean isUnderline) {
    if (isUnderline) {
      map.forEach((str, obj) -> {
        String newKey = underlineToCamel(str, false);
        if (!newKey.equals(str)) {
          map.putIfAbsent(newKey, obj);
          map.remove(str);
        }
      });
    }
    fromMap(map);
    return (T) this;
  }

  private MoreBatis getMoreBatis() {
    return (MoreBatis) SpringContextHolder.getBean("moreBatis");
  }

  /*public T insert() {
    getMoreBatis().insertEntity((T) this);
    return (T) this;
  }

  public T delete() {
    getMoreBatis().deleteEntity((T) this);
    return (T) this;
  }

  public T update() {
    getMoreBatis().updateEntity((T) this);
    return (T) this;
  }


  public T selectByPks() {
    return (T) getMoreBatis().selectEntityByPks((T) this);
  }*/


  public Map getEtc() {
    return etc;
  }

  public void setEtc(Map etc) {
    this.etc = etc;
  }
}
