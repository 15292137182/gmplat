package com.bcx.plat.core.controller;


import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

/**
 * Created by Went on 2017/7/30.
 */
@Controller
public class TestController extends BaseController {

    @Autowired
    private TestService testServiceImpl;

    @RequestMapping("/test")
    @ResponseBody
    public ServiceResult test(int id) {
        List list = testServiceImpl.testService(id);
        logger.info("sussess");
        return new ServiceResult(STATUS_SUCCESS, "", list);
    }

    @RequestMapping("/base")
    public void exchangeJson(int id, HttpServletRequest request, HttpServletResponse response) {
        List list = testServiceImpl.testService(id);
        JsonCallback.Callback(request, response, list);
    }


}
