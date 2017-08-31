package com.bcx.config;

import com.bcx.plat.core.utils.PropertiesLoader;

/**
 * 基础配置
 * <p>
 * Create By HCL at 2017/8/31
 */
public class Global {

  private Global() {
  }

  private static PropertiesLoader propertiesLoader = new PropertiesLoader("properties/gmplat.properties");

  /**
   * 获取配置文件中的属性
   *
   * @param key key
   * @return 返回属性，取不到时为空
   */
  public static String getProperty(String key) {
    return propertiesLoader.getProperty(key);
  }
}
