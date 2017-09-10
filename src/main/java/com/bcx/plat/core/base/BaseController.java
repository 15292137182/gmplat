package com.bcx.plat.core.base;

import com.bcx.plat.core.utils.SystemResult;
import com.bcx.plat.core.utils.ServerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 基础控制器
 */
public abstract class BaseController {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  protected ResourceBundleMessageSource messageSource;


  /**
   * 对返回的结果进行处理
   *
   * @param request 请求
   * @param systemResult 结果信息
   * @param locale 国际化
   * @return 返回
   */
  protected Map result(HttpServletRequest request, SystemResult systemResult, Locale locale) {
    Map map = new HashMap();
    ServerResult serviceResult = (ServerResult) systemResult.get("content");
    String msg = serviceResult.getMsg();

    if (null != serviceResult) {
      String message = messageSource.getMessage(msg, null, locale);
      if (isValid(message)) {
        serviceResult.setMsg(message);
        map.put("resp",serviceResult);
      }
//      if (isValid(request.getParameter("callback"))) {
//        map.put("resp",serviceResult);
//        MappingJacksonValue value = new MappingJacksonValue(map);
//        value.setJsonpFunction(request.getParameter("callback"));
//        return value;
//      }
    }
    return map;
  }

}
