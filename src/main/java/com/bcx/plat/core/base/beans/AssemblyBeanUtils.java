package com.bcx.plat.core.base.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 组合 bean 工具类
 * <p>
 * Create By HCL at 2017/8/29
 */
public abstract class AssemblyBeanUtils<T extends AssemblyBeanUtils> implements BeanLimit<T> {

  // 注册 class
  private transient Map<Class<? extends BeanLimit>, Object> assemblyRegister = new HashMap<>();
  private transient Map assemblyExtraData = new HashMap();

  /**
   * 将 javaBean 转换为 Bean
   *
   * @return 返回
   */
  public Map toMap() {
    // 复合 bean 注意变量污染
    Map map = new HashMap();
    map.putAll(assemblyExtraData);
    for (Class _class : assemblyRegister.keySet()) {
      Object obj = assemblyRegister.get(_class);
      if (obj instanceof BeanLimit) {
        map.putAll(((BeanLimit) obj).toMap());
      }
    }
    return map;
  }

  /**
   * 从 map 中读取数据
   *
   * @param map map
   * @return 返回自身
   */
  @SuppressWarnings("unchecked")
  public T fromMap(Map map) {
    if (map != null) {
      Set explicitKey = toMap().keySet();
      for (Class _class : assemblyRegister.keySet()) {
        Object o = assemblyRegister.get(_class);
        BeanLimit bl = null;
        if (o == null) {
          try {
            bl = (BeanLimit) _class.newInstance();
            bl.fromMap(map);
          } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
          }
        } else if (o instanceof BeanLimit) {
          bl = (BeanLimit) o;
          bl.fromMap(map);
        }
        assemblyRegister.put(_class, bl);
      }
      for (Object key : map.keySet()) {
        if (!explicitKey.contains(key)) {
          assemblyExtraData.put(key, map.get(key));
        }
      }
    }
    return (T) this;
  }

  /**
   * 获取指定类型的class
   *
   * @param clazz class
   * @param <TY>  类型
   * @return 返回
   */
  @SuppressWarnings("unchecked")
  public <TY> TY getSubAssembly(TY clazz) {
    return (TY) assemblyRegister.get(clazz);
  }

  /**
   * @return 所有注册的 class
   */
  public Set<Class<? extends BeanLimit>> getRegisterClass() {
    return assemblyRegister.keySet();
  }

  /**
   * 获取扩展字段
   *
   * @return 返回
   */
  public Map getExtraData() {
    return this.assemblyExtraData;
  }

  /**
   * 注册 javaBean
   *
   * @param clazz class类
   * @return 返回自身
   */
  @SuppressWarnings("unchecked")
  public T registerBean(Class<? extends BeanLimit>... clazz) {
    if (null != clazz && clazz.length != 0) {
      for (Class _class : clazz) {
        try {
          assemblyRegister.put(_class, _class.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return (T) this;
  }

}
