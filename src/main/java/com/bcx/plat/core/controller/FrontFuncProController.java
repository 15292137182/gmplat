package com.bcx.plat.core.controller;

import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;



/**
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping("/core")
public class FrontFuncProController {

    @Autowired
    private FrontFuncProService frontFuncProService;
    /**
     *  查询前端功能块属性项信息
     * @param str 接受传入进来的map参数
     * @param rowId 接受传入进来的rowId
     * @return 返回查询数据以及状态
     */
    @RequestMapping("/queryFronFuncPro")
    public MappingJacksonValue queryFronFuncPro(String str , String rowId, HttpServletRequest request, Locale locale){
        Map<String, Object> map = new HashMap<>();
        map.put("strArr", collectToSet(str));
        map.put("rowId",rowId);
        ServiceResult<FrontFuncPro> query = frontFuncProService.queryFronFuncPro(map);
        MappingJacksonValue result = new MappingJacksonValue(query.convertMsg(locale));
        result.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return result;

    }

    /**
     * 新增前端功能块属性项信息维护
     * @param frontFuncPro 前端功能块属性实体
     * @return 返回新增状态
     */
    @RequestMapping("/addFronFuncPro")
    public MappingJacksonValue addFronFuncPro(FrontFuncPro frontFuncPro,HttpServletRequest request,Locale locale){
        ServiceResult result = frontFuncProService.addFronFuncPro(frontFuncPro);
        MappingJacksonValue value = new MappingJacksonValue(result.convertMsg(locale));
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 修改前端功能块属性项信息维护
     * @param frontFuncPro 前端功能块属性实体
     * @return 返回修改状态
     */
    @RequestMapping("/modifyFronFuncPro")
    public MappingJacksonValue modifyFronFuncPro(FrontFuncPro frontFuncPro , HttpServletRequest request,Locale locale){
        ServiceResult result = frontFuncProService.modifyFronFuncPro(frontFuncPro);
        MappingJacksonValue value = new MappingJacksonValue(result.convertMsg(locale));
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 删除前端功能块属性项信息维护
     * @param delData 接受需要删除数据的id
     * @return 返回删除的状态
     */
    @RequestMapping("/delFrontFuncPro")
    public MappingJacksonValue delete(String delData, HttpServletRequest request,Locale locale){
        ServiceResult<FrontFuncPro> result = frontFuncProService.delete(delData);
        MappingJacksonValue value = new MappingJacksonValue(result.convertMsg(locale));
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;

    }



}
