package com.bcx.plat.core.servlet;

import com.bcx.plat.core.manager.TXManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bcx.plat.core.utils.ServletUtils.uriNeedHandle;

/**
 * 新增异常通知
 * Create By HCL at 2017/8/14
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {

  /**
   * 当找不到处理 URI 的控制器时的处理方式
   *
   * @param request  请求
   * @param response 返回
   * @throws Exception 可能抛出的异常
   */
  @Override
  protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
    super.noHandlerFound(request, response);
  }

  /**
   * 执行 doDispatch，开其事务管理
   *
   * @param request  请求
   * @param response 返回
   */
  @Override
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 需要平台处理时开启事务
    if (uriNeedHandle(request.getRequestURI())) {
      TXManager.doInRequiredTX(((manager, status) -> super.doDispatch(request, response)));
    } else {
      super.doDispatch(request, response);
    }
  }

}
