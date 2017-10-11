package com.bcx.plat.core.constants;

import com.bcx.plat.core.utils.PropertiesLoader;

/**
 * Create By HCL at 2017/9/1
 */
public abstract class Global {

  private static PropertiesLoader loader = new PropertiesLoader("properties/gmplat.properties");

  public static final String PLAT_SYS_PREFIX = "/gmp/sys";

  /**
   * 获取属性名称
   *
   * @return 返回属性值
   */
  public static Object getValue(String key) {
    Object obj = System.getProperty("key");
    if (null != obj) {
      return obj;
    }
    obj = loader.getProperty(key);
    if (null != obj) {
      return obj;
    }
    return null;
  }

  /**
   * 获取 String 类型的值
   *
   * @param key 值
   * @return 返回
   */
  public static String getValueAsString(String key) {
    Object _obj = getValue(key);
    if (null != _obj) {
      return String.valueOf(_obj);
    }
    return null;
  }

}