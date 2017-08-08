package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.utils.ServiceResult;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Went on 2017/8/1.
 */
@RestController
@RequestMapping("/core/businObjPro")
public class BusinessObjectProController extends BaseController {

    @Autowired
    private BusinessObjectProService businessObjectProService;


    /**
     * 查询业务对象属性
     *
     * @param str    根据代码和名称查询数据
     * @param rowId  根据rowId查询出业务对象关联的数据
     * @param rowIds 根据roeIds查询属性数据
     * @return
     */
    @RequestMapping("/query")
    public Object select(String str, String rowId, String rowIds, HttpServletRequest request, Locale locale) {
        Map<String, Object> map = new HashMap<>();
        map.put("rowId", rowId);
        map.put("strArr", collectToSet(str));
        map.put("rowIds", rowIds);
        ServiceResult<BusinessObjectPro> result = businessObjectProService.select(map);
        return super.result(request, result, locale);
    }

    /**
     * 新增业务对象属性
     */
    @RequestMapping("/add")
    public Object insert(BusinessObjectPro businessObjectPro,
                         HttpServletRequest request, Locale locale) {
        ServiceResult<BusinessObjectPro> result = businessObjectProService.insert(businessObjectPro);
        return super.result(request, result, locale);
    }

    /**
     * 删除业务对象属性
     */
    @RequestMapping("/delete")
    public Object dalete(String delData, HttpServletRequest request, Locale locale) {
        ServiceResult<BusinessObjectPro> result = businessObjectProService.delete(delData);
        return super.result(request, result, locale);
    }
}
