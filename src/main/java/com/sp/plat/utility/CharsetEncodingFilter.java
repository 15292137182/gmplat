package com.sp.plat.utility;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Went on 2017/7/27.
 */
public class CharsetEncodingFilter implements Filter {

    private String encoding = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        //获取初始化参数
        encoding = filterConfig.getInitParameter("encoding");
        System.out.println("获取编码格式" + encoding);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //判断字符编码是否为空
        if (encoding != null) {
            //设置Request的编码格式和Response的字符编码
            request.setCharacterEncoding(encoding);
            response.setContentType("text/html;charset=" + encoding);
            System.out.println("编码格式为:" + encoding);
        }
        //传递给下一个过滤器
        filterChain.doFilter(request, response);

    }

    public void destroy() {
        encoding = null;
    }
}
