package com.bcx.plat.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties文件载入工具类. 可载入多个properties文件
 * 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以 System 的Property优先.
 * <p>
 * Create By HCL at 2017/8/31
 */
public class PropertiesLoader {

  private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
  private static ResourceLoader resourceLoader = new DefaultResourceLoader();
  private final Properties properties;

  public PropertiesLoader(String... resourcesPaths) {
    properties = loadProperties(resourcesPaths);
  }

  public Properties getProperties() {
    return properties;
  }

  /**
   * 取出Property，但以System的 Property 优先,取不到返回空字符串.
   *
   * @param key 键
   * @return 返回
   */
  private String getValue(String key) {
    String systemProperty = System.getProperty(key);
    if (systemProperty != null) {
      return systemProperty;
    }
    if (properties.containsKey(key)) {
      return properties.getProperty(key);
    }
    return "";
  }

  /**
   * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
   *
   * @param key 键
   * @return 返回
   */
  public String getProperty(String key) {
    return getValue(key);
  }

  /**
   * 取出 String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
   *
   * @param key          键
   * @param defaultValue 默认值
   * @return 返回
   */
  public String getProperty(String key, String defaultValue) {
    String value = getValue(key);
    return value != null ? value : defaultValue;
  }

  /**
   * 载入多个文件, 文件路径使用 Spring Resource 格式.
   *
   * @param resourcesPaths 路径
   * @return 返回 Properties
   */
  private Properties loadProperties(String... resourcesPaths) {
    Properties props = new Properties();
    for (String location : resourcesPaths) {
      InputStream is = null;
      try {
        Resource resource = resourceLoader.getResource(location);
        is = resource.getInputStream();
        props.load(is);
      } catch (IOException ex) {
        logger.error("找不到指定的配置文件 :" + location + ", " + ex.getMessage());
      } finally {
        if (null != is) {
          try {
            is.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return props;
  }

}
