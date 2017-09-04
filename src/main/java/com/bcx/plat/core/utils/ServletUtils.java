package com.bcx.plat.core.utils;

import com.bcx.plat.core.constants.Global;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * Create By HCL at 2017/8/31
 */
public class ServletUtils {

  private ServletUtils() {

  }

  /**
   * 判断 URL 请求是否为静态文件请求
   *
   * @param uri uri
   * @return 返回布尔类型
   */
  public static boolean uriNeedHandle(String uri) {
    // 如果以应用程序名开头则去除
    String _appName = SpringContextHolder.getApplicationContext().getApplicationName();
    if (uri.startsWith(_appName)) {
      uri = uri.replaceFirst(_appName, "");
    }
    if (!uri.startsWith(PLAT_SYS_PREFIX)) {
      return false;
    } else {
      uri = uri.replaceFirst(PLAT_SYS_PREFIX, "");
      String _uris = Global.getStringValue("web.platURIPrefix");
      if (null != _uris) {
        String[] uris = _uris.split(",");
        for (String _uri : uris) {
          if (uri.startsWith(_uri)) return true;
        }
      }
    }

    return false;
  }
}
