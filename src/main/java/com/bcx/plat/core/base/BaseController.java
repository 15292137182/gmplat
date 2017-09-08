package com.bcx.plat.core.base;

import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
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
   * 转换消息
   *
   * @param msg    消息代码
   * @param locale 本地化信息
   * @return 返回消息
   */

  protected String convertMsg(String msg, Locale locale) {
    String _msg = null;
    try {
      _msg = messageSource.getMessage(msg, null, locale);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (null != _msg) {
      return _msg;
    }
    return msg;
  }

  /**
   * 对返回的结果进行处理
   *
   * @param request    请求
   * @param platResult 结果信息
   * @param locale     国际化
   * @return 返回
   */
  @SuppressWarnings("unchecked")
  protected Object result(HttpServletRequest request, PlatResult platResult, Locale locale) {
    Map map = new HashMap();
    ServerResult content = platResult.getContent();
    platResult.getContent().setMsg(convertMsg(content.getMsg(), locale));
    map.put("resp", platResult);
    if (isValid(request.getParameter("callback"))) {
      map.put("resp", platResult);
      MappingJacksonValue value = new MappingJacksonValue(map);
      value.setJsonpFunction(request.getParameter("callback"));
      return value;
    }
    return map;
  }

  /**
   * 接受参数和消息进行封装
   *
   * @param content 接受的参数
   * @param msg     消息
   * @param <T>     参数泛型
   * @return 返回
   */
  protected <T> PlatResult commonServiceResult(T content, String msg) {
    return PlatResult.Msg(new ServerResult<>(BaseConstants.STATUS_SUCCESS, msg, content));
  }
}
