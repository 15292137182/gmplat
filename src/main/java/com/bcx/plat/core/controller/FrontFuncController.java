package com.bcx.plat.core.controller;

import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * Created by Went on 2017/8/2.
 */
@RequestMapping("/fronc")
@RestController
public class FrontFuncController {

    @Autowired
    private FrontFuncService frontFuncService;


    @RequestMapping("/select")
    public MappingJacksonValue select(String str, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("StrArr", str);
        List<FrontFunc> select = frontFuncService.select(map);
        MappingJacksonValue mapjack = new MappingJacksonValue(new ServiceResult<FrontFunc>(select));
        mapjack.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return mapjack;
    }

    @RequestMapping("/insert")
    public MappingJacksonValue insert(FrontFunc frontFunc, HttpServletRequest request) {
        String insert = frontFuncService.insert(frontFunc);
        MappingJacksonValue mapjack = new MappingJacksonValue(new ServiceResult<FrontFunc>(insert));
        mapjack.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return mapjack;
    }

    @RequestMapping("/update")
    public MappingJacksonValue update(FrontFunc frontFunc, HttpServletRequest request) {
        int update = frontFuncService.update(frontFunc);
        MappingJacksonValue mapjack = new MappingJacksonValue(new ServiceResult<FrontFunc>(update));
        mapjack.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return mapjack;
    }

    @RequestMapping("/delete")
    public MappingJacksonValue delete(FrontFunc frontFunc, HttpServletRequest request) {
        int delete = frontFuncService.delete(frontFunc);
        MappingJacksonValue mapjack = new MappingJacksonValue(new ServiceResult<FrontFunc>(delete));
        mapjack.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return mapjack;
    }


}
