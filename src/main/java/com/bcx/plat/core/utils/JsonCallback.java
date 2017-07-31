package com.bcx.plat.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.bcx.plat.core.utils.UtilsTool.objToJson;

/**
 * Created by Went on 2017/7/31.
 */
public class JsonCallback {

    /**
     * 获取前端页面传递过来的jsonCallback参数
     *
     * @param request
     * @param response
     * @param obj
     * @return
     */
    public static void Callback(HttpServletRequest request, HttpServletResponse response, Object obj) {
        PrintWriter out = null;
        try {
            response.setContentType("text/plain");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            out = response.getWriter();
            String jsonpCallback = request.getParameter("jsonpCallback");//客户端请求参数
            out.println(jsonpCallback + "(" + objToJson(obj) + ")");//返回jsonp格式数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}
