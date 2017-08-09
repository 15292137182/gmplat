package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
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
        //默认新增数据为失效状态
        businessObject.setStatus(BaseConstants.INVALID);
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
