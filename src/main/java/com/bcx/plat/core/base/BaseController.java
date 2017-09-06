package com.bcx.plat.core.base;

import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.json.MappingJacksonValue;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 基础控制器
 */
public abstract class BaseController {

  /**
   * logger 日志操作
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * messageSource 资源文件管理器
   */
  @Autowired
  protected ResourceBundleMessageSource messageSource;

  /**
   * 对返回的结果进行处理
   *
   * @param request       请求
   * @param serviceResult 结果信息
   * @param locale        国际化
   * @return 返回
   */
  @SuppressWarnings("unchecked")
  protected Object result(HttpServletRequest request, ServiceResult serviceResult, Locale locale) {
    Map map = new HashMap();
    PlatResult content = serviceResult.getContent();
    String msg = (content == null ? null : content.getMsg());
    String message = null;
    if (null != msg) {
      try {
        message = messageSource.getMessage(msg, null, locale);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (isValid(message)) {
      serviceResult.getContent().setMsg(isValid(message) ? message : msg);
      map.put("resp", serviceResult);
    }
    if (isValid(request.getParameter("callback"))) {
      map.put("resp", serviceResult);
      MappingJacksonValue value = new MappingJacksonValue(map);
      value.setJsonpFunction(request.getParameter("callback"));
      return value;
    }
    return map;
  }
}
