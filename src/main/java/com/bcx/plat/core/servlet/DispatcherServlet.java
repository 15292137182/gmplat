package com.bcx.plat.core.servlet;

import com.bcx.plat.core.manager.TXManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bcx.plat.core.utils.ServletUtils.isStaticFile;

/**
 * Create By HCL at 2017/8/14
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {

  /**
   * 执行 doDispatch，开其事务管理
   *
   * @param request  请求
   * @param response 返回
   */
  @Override
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (isStaticFile(request.getRequestURI())) {
      super.doDispatch(request, response);
    } else {
      // 静态文件不在开启事务
      TXManager.doInRequiredTX(((manager, status) -> super.doDispatch(request, response)));
    }
  }

}
