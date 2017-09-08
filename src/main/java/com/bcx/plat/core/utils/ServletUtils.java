package com.bcx.plat.core.utils;

import com.bcx.plat.core.constants.Global;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

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

  /**
   * 获取国际化信息
   *
   * @param messageKey 国际化信息的 key
   * @param params     参数(如果需要)
   * @return 返回
   */
  protected static String getMessage(String messageKey, String... params) {
    String message = null;
    try {
      // 获取当前的国际化语言
      String languageTag = String.valueOf(
              ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                      .getRequest()
                      .getAttribute("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE"));
      Locale locale = Locale.forLanguageTag(languageTag);
      ResourceBundleMessageSource messageSource = SpringContextHolder.getBean(ResourceBundleMessageSource.class);
      message = messageSource.getMessage(messageKey, params, locale);
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    if (null != message) {
      return message;
    }
    return messageKey;
  }
}
