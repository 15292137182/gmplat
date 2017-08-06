package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.utils.ServiceResult;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Went on 2017/8/1.
 */
@RestController
@RequestMapping("/businObjPro")
public class BusinessObjectProController {

    @Autowired
    private BusinessObjectProService businessObjectProService;


    /**
     * 查询业务对象属性
     *
     * @param str     根据代码和名称查询数据
     * @param rowId   业务对象rowId
     * @param request 请求
     * @return
     */
    @RequestMapping("/select")
    public MappingJacksonValue select(String str, String rowId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("rowId", rowId);
        map.put("strArr", collectToSet(str));
        ServiceResult<BusinessObjectPro> result = businessObjectProService.select(map);
        MappingJacksonValue value = new MappingJacksonValue(result);
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 新增业务对象属性
     */
    @RequestMapping("/insert")
    public MappingJacksonValue insert(BusinessObjectPro businessObjectPro,
                                      HttpServletRequest request) {
        ServiceResult<BusinessObjectPro> result = businessObjectProService.insert(businessObjectPro);
        MappingJacksonValue value = new MappingJacksonValue(result);
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 删除业务对象属性
     */
    @RequestMapping("/delete")
    public MappingJacksonValue dalete(String rowId, HttpServletRequest request) {
        ServiceResult<BusinessObjectPro> result = businessObjectProService.delete(rowId);
        MappingJacksonValue value = new MappingJacksonValue(result);
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }
}
