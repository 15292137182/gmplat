package com.bcx.plat.core.controller;

import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.service.KeySetService;
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
 * Created by Went on 2017/8/3.
 */
@RequestMapping("/keySet")
@RestController
public class KeySetController {

    @Autowired
    private KeySetService keySetService;

    /**
     *
     * @param str  根据条件查询数据
     * @param rowId 根据ID查询数据
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/select")
    public MappingJacksonValue select(String str,String rowId, HttpServletRequest request, Locale locale){
        Map<String,Object> map = new HashMap<>();
        map.put("strArr",collectToSet(str));
        map.put("rowId",rowId);
        ServiceResult<KeySet> selete = keySetService.selete(map);
        MappingJacksonValue result = new MappingJacksonValue(selete.convertMsg(locale));
        result.setJsonpFunction(isValid(request.getParameter("callback"))?request.getParameter("callback"):"callback");
        return result;
    }

    /**
     * 新增键值集合信息
     * @param keySet 键值集合信息实体
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/insert")
    public MappingJacksonValue insert(KeySet keySet,HttpServletRequest request,Locale locale){
        ServiceResult<KeySet> insert = keySetService.insert(keySet);
        MappingJacksonValue result = new MappingJacksonValue(insert.convertMsg(locale));
        result.setJsonpFunction(isValid(request.getParameter("callback"))?request.getParameter("callback"):"callback");
        return result;
    }

    /**
     * 修改键值集合信息
     * @param keySet 键值集合信息实体
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/update")
    public MappingJacksonValue update(KeySet keySet,HttpServletRequest request,Locale locale){
        ServiceResult<KeySet> insert = keySetService.update(keySet);
        MappingJacksonValue result = new MappingJacksonValue(insert.convertMsg(locale));
        result.setJsonpFunction(isValid(request.getParameter("callback"))?request.getParameter("callback"):"callback");
        return result;
    }

    /**
     * 删除键值集合信息
     * @param keySet 键值集合信息实体
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    public MappingJacksonValue delete(KeySet keySet,HttpServletRequest request,Locale locale){
        ServiceResult<KeySet> insert = keySetService.delete(keySet);
        MappingJacksonValue result = new MappingJacksonValue(insert.convertMsg(locale));
        result.setJsonpFunction(isValid(request.getParameter("callback"))?request.getParameter("callback"):"callback");
        return result;
    }
}
