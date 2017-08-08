package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端功能模块 Created by Went on 2017/8/2.
 */
@RequestMapping("/core/fronc")
@RestController
public class FrontFuncController extends BaseController {

    @Autowired
    private FrontFuncService frontFuncService;


    /**
     * 查询前端功能模块
     */
    @RequestMapping("/query")
    public Object select(String str, String rowId, HttpServletRequest request,
                         Locale locale) {
        Map<String, Object> map = new HashMap<>();
        map.put("strArr", collectToSet(str));
        map.put("rowId", rowId);
        ServiceResult<FrontFunc> result = frontFuncService.select(map);
        return super.result(request, result, locale);
    }

    /**
     * 新增前端功能模块
     */
    @RequestMapping("/add")
    public Object insert(FrontFunc frontFunc, HttpServletRequest request,
                         Locale locale) {
        ServiceResult<FrontFunc> result = frontFuncService.insert(frontFunc);
        return super.result(request, result, locale);
    }

    /**
     * 更新前端功能模块
     */
    @RequestMapping("/modify")
    public Object update(FrontFunc frontFunc, HttpServletRequest request,
                         Locale locale) {
        ServiceResult<FrontFunc> result = frontFuncService.update(frontFunc);
        return super.result(request, result, locale);

    }

    /**
     * 删除前端功能模块
     */
    @RequestMapping("/delete")
    public Object delete(FrontFunc frontFunc, HttpServletRequest request,
                         Locale locale) {
        ServiceResult<FrontFunc> result = frontFuncService.delete(frontFunc);
        return super.result(request, result, locale);

    }


}
