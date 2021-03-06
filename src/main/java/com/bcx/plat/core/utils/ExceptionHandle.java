package com.bcx.plat.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 本框架任何一处抛出异常时，若该异常没有被程序内部主动捕捉，都将调用该异常处理器 前提是该异常由 servlet 触发 Created by HCL on 2017/7/14.
 */
public class ExceptionHandle implements HandlerExceptionResolver {

  /**
   * 全局异常处理器，发生异常时的处理方法
   *
   * @param request 请求
   * @param response 回复
   * @param handler 处理器
   * @param ex 异常
   * @return 返回新的视图
   */
  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    try {
      PrintWriter writer = response.getWriter();
      if ((ex).getCause() instanceof PSQLException) {
        writer.write("dataError");

      }
      writer.flush();
      writer.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
