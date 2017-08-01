package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.MaintTableInfo;
import com.bcx.plat.core.service.MaintTableService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

/**
 * Created by Went on 2017/7/31.
 */
@Controller
@RequestMapping("/maint")
public class MaintTableController extends BaseController {

    @Autowired
    private MaintTableService maintTableService;

    @RequestMapping("/select")
    @ResponseBody
    public MappingJacksonValue select(String str, HttpServletRequest request, HttpServletResponse response) {
        List result = maintTableService.selectMaint(str);
        MappingJacksonValue value = new MappingJacksonValue(objToJson(new ServiceResult("消息传递成功", result)));
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    @RequestMapping("/selectById")
    @ResponseBody
    public MappingJacksonValue selectById(String rowId, HttpServletRequest request, HttpServletResponse response) {
        List<MaintTableInfo> result = maintTableService.selectById(rowId);
        MappingJacksonValue value = new MappingJacksonValue(objToJson(new ServiceResult("消息传递成功", result)));
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }


}
