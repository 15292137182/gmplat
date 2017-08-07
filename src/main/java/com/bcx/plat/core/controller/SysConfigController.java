package com.bcx.plat.core.controller;

import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.service.SysConfigService;
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
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;
    /**
     *  查询资源配置信息
     * @param str 接受传入进来的map参数
     * @param rowId 接受传入进来的rowId
     * @return 返回查询数据以及状态
     */
    @RequestMapping("/querySysConfig")
    public MappingJacksonValue querySysConfig(String str , String rowId, HttpServletRequest request, Locale locale){
        Map<String, Object> map = new HashMap<>();
        map.put("strArr", collectToSet(str));
        map.put("rowId",rowId);
        ServiceResult<SysConfig> query = sysConfigService.querySysConfig(map);
        MappingJacksonValue result = new MappingJacksonValue(query.convertMsg(locale));
        result.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return result;

    }

    /**
     * 新增资源配置信息
     * @param sysConfig 资源配置信息实体
     * @return 返回新增状态
     */
    @RequestMapping("/addSysConfig")
    public MappingJacksonValue addSysConfig(SysConfig sysConfig,HttpServletRequest request,Locale locale){
        ServiceResult result = sysConfigService.addSysConfig(sysConfig);
        MappingJacksonValue value = new MappingJacksonValue(result.convertMsg(locale));
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 修改资源配置信息
     * @param sysConfig 资源配置信息实体
     * @return 返回修改状态
     */
    @RequestMapping("/modifySysConfig")
    public MappingJacksonValue modifySysConfig(SysConfig sysConfig , HttpServletRequest request,Locale locale){
        ServiceResult result = sysConfigService.modifySysConfig(sysConfig);
        MappingJacksonValue value = new MappingJacksonValue(result.convertMsg(locale));
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 删除资源配置信息
     * @param delData 接受需要删除数据的id
     * @return 返回删除的状态
     */
    @RequestMapping("/delSysConfig")
    public MappingJacksonValue delete(String delData, HttpServletRequest request,Locale locale){
        ServiceResult<SysConfig> result = sysConfigService.deleteSysConfig(delData);
        MappingJacksonValue value = new MappingJacksonValue(result.convertMsg(locale));
        value.setJsonpFunction(
                isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;

    }



}
