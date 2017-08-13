package com.bcx.plat.core.filter;

import com.bcx.plat.core.manager.TXManager;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 系统事务拦截器
 *
 * 自主研发事务轮子     -。-
 *
 * Create By HCL at 2017/8/13
 */
public class SpTxFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    TXManager.doInNewTX(((manager, status) -> {
      try {
        filterChain.doFilter(request, response);
      } catch (Exception e) {
        e.printStackTrace();
        if (!status.isCompleted()) {
          manager.rollback(status);
        }
      } finally {
        if (!status.isCompleted()) {  // 正常情况下事务不会结束

        }
      }
    }));
  }
}
