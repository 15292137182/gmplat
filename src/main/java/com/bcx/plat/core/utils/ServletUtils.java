package com.bcx.plat.core.utils;

import com.bcx.config.Global;

/**
 * Create By HCL at 2017/8/31
 */
public class ServletUtils {

  private ServletUtils() {
  }

  // 静态文件后缀
  private final static String[] staticFiles = Global.getProperty("web.staticFile").split(",");

  /**
   * 判断 URL 请求是否为静态文件请求
   *
   * @param uri uri
   * @return 返回布尔类型
   */
  public static boolean isStaticFile(String uri) {
    for (String _uri : staticFiles) {
      if (uri.endsWith(_uri))
        return true;
    }
    return false;
  }

}
