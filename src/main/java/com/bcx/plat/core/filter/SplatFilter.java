package com.bcx.plat.core.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 平台过滤器，对传入 URL 的 URL 进行基本过滤,已有过滤条件：
 *
 * Create By HCL at 2017/8/20
 */
public class SplatFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  /**
   * 执行过滤，如果最后没有执行 chain.doFilter(request, response)，该请求将会中断
   *
   * @param request 请求
   * @param response 返回
   * @param chain 过滤链
   * @throws IOException IO
   * @throws ServletException Servlet
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}
