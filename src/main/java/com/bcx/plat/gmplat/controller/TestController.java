package com.bcx.plat.gmplat.controller;


import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.utils.JsonResult;
import com.bcx.plat.gmplat.service.TestService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Went on 2017/7/30.
 */
@Controller
public class TestController extends BaseController {

    @Autowired
    private TestService testServiceImpl;

    @RequestMapping("/test")
    @ResponseBody
    public JsonResult test(int id){
        List list = testServiceImpl.testService(id);
        logger.info("sussess");
        return new JsonResult(list);
    }

    @RequestMapping("/base")
    @ResponseBody
    public void exchangeJson(int id,HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/plain");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            List list = testServiceImpl.testService(id);
            JsonResult jsonResult = new JsonResult(list);
            PrintWriter out = response.getWriter();
            JSONObject resultJSON = JSONObject.fromObject(jsonResult); //根据需要拼装json
            String jsonpCallback = request.getParameter("jsonpCallback");//客户端请求参数
            out.println(jsonpCallback+"("+resultJSON.toString()+")");//返回jsonp格式数据
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
