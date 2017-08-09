package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by WJF on 2017/8/8.
 */
@RestController
@RequestMapping("/core/businObj")
public class BusinessObjectController extends BaseControllerTemplate<BusinessObjectService, BusinessObject> {

    @Autowired
    BusinessObjectService businessObjectService;

    @Autowired
    private BusinessObjectProService businessObjectProService;


    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public void setBusinessObjectProService(BusinessObjectProService businessObjectProService) {
        this.businessObjectProService = businessObjectProService;
    }

    /**
     * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     *
     * @param rowId
     * @param request
     * @param locale
     */
    @RequestMapping("/query")
    @Override
    public Object singleInputSelect(String rowId, HttpServletRequest request, Locale locale) {
//        if (str.length()!=0) {
//            ServiceResult<List<Map<String, Object>>> result = businessObjectProService
//                    .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));
//            return super.result(request, result, locale);
//        }
        Map<String,Object> map = new HashMap<>();
        map.put("rowId",rowId);
        ServiceResult<List<Map<String, Object>>> selectList = businessObjectService.select(map);
        List<Map<String, Object>> data = selectList.getData();
        if (data != null) {
            ServiceResult<List<Map<String, Object>>> result = businessObjectProService.singleInputSelect(Arrays.asList("rowId"), Arrays.asList("rowId"));
            return super.result(request, result, locale);
        }
        return super.result(request, selectList, locale);
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }


    /**
     * 新增
     *
     * @param businessObject
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @Override
    public Object insert(BusinessObject businessObject, HttpServletRequest request, Locale locale) {
        businessObject.setVersion("1.0");
        ServiceResult<Map<String, Object>> result = businessObjectService.insert(businessObject.buildCreateInfo().toMap());
        return super.result(request, result, locale);
    }

    /**
     * 删除业务对象
     *
     * @param rowId
     * @param request
     * @param locale
     */
    @RequestMapping("/delete")
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        ServiceResult<List<Map<String, Object>>> list = businessObjectProService.singleInputSelect(Arrays.asList("objRowId"), Arrays.asList(rowId));
        List<Map<String, Object>> data = list.getData();
        if (data != null) {
            List<String> rowIds = data.stream().map((row) -> {
                return (String) row.get("rowId");
            }).collect(Collectors.toList());
            Map<String, Object> argsSub = new HashMap<>();
            argsSub.put("rowId", rowIds);
            businessObjectProService.delete(argsSub);
        }
        Map<String, Object> args = new HashMap<>();
        args.put("rowId", rowId);
        ServiceResult<Object> result = businessObjectService.delete(args);
        return super.result(request, result, locale);
    }


}
