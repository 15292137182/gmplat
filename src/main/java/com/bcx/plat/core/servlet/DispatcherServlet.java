package com.bcx.plat.core.servlet;

import com.bcx.plat.core.manager.TXManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create By HCL at 2017/8/14
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {

  /**
   * 执行 doDispatch，开其事务管理
   *
   * @param request 请求
   * @param response 返回
   * @throws Exception 可能抛出的异常
   */
  @Override
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    TXManager.doInNewTX(((manager, status) -> {
      try {
        super.doDispatch(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }));

  }
}
