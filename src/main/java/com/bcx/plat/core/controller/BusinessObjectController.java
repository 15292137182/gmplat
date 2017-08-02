package com.bcx.plat.core.controller;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * Created by Went on 2017/8/1.
 */
@RestController
@RequestMapping("/businObj")
public class BusinessObjectController {

    @Autowired
    private BusinessObjectService businessObjectService;
    /**
     * 查询业务对象
     * 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     * @param str
     * @return
     */
    @RequestMapping("/select")
    public MappingJacksonValue select(String str, HttpServletRequest request){
        Map<String, Object> cond = new HashMap<>();
        cond.put("strArr", collectToSet(str));
        ServiceResult<BusinessObject> result = businessObjectService.select(cond);
        MappingJacksonValue value = new MappingJacksonValue(result);
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 新增业务对象:对象代码，对象名称，关联表(单选)，版本(系统生成)
     * @param businessObject
     * @return
     */
    @RequestMapping("/insert")
    public MappingJacksonValue insert(BusinessObject businessObject,HttpServletRequest request){
        ServiceResult<BusinessObject> result = businessObjectService.insert(businessObject);
        MappingJacksonValue value = new MappingJacksonValue(result);
        value.setJsonpFunction(isValid(request.getParameter("callback"))?request.getParameter("callback"):"callback");
        return value;
    }
    

    /**
     * 编辑业务对象名称字段
     * @param businessObject
     * @return
     */
    @RequestMapping("/update")
   public MappingJacksonValue update(BusinessObject businessObject,HttpServletRequest request){
       ServiceResult<BusinessObject> result = businessObjectService.update(businessObject);
       MappingJacksonValue value = new MappingJacksonValue(result);
       value.setJsonpFunction(isValid(request.getParameter("callback"))?request.getParameter("callback"):"callback");
       return value;
   }

    /**
     * 删除业务对象
     * @param rowId
     * @return
     */
    @RequestMapping("/delete")
    public MappingJacksonValue dalete(String rowId,HttpServletRequest request){
        ServiceResult<BusinessObject> result = businessObjectService.delete(rowId);
        MappingJacksonValue value = new MappingJacksonValue(result);
        value.setJsonpFunction(isValid(request.getParameter("callback"))?request.getParameter("callback"):"callback");
        return value;
    }
}
