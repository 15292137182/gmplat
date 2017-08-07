package com.bcx.plat.core.base;

import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

import com.bcx.plat.core.utils.ServiceResult;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 基础控制器
 */
public class BaseController {

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
   * @param request 请求
   * @param serviceResult 结果信息
   * @param locale 国际化
   * @return 返回
   */
  protected Object result(HttpServletRequest request, ServiceResult serviceResult, Locale locale) {
    if (null != serviceResult) {
      String message = messageSource.getMessage(serviceResult.getMessage(), null, locale);
      if (isValid(message)) {
        serviceResult.setMessage(message);
      }
      if (isValid(request.getParameter("callback"))) {
        StringBuilder sb = new StringBuilder(request.getParameter("callback"));
        return sb.append("(").append(objToJson(serviceResult)).append(")");
      }
    }
    return serviceResult;
  }
}
