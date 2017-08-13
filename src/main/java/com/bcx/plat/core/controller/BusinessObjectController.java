package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by WJF on 2017/8/8.
 */
@RestController
@RequestMapping("/core/businObj")
public class BusinessObjectController extends
        BaseControllerTemplate<BusinessObjectService, BusinessObject> {

    @Autowired
    private BusinessObjectProService businessObjectProService;

    public void setBusinessObjectProService(BusinessObjectProService businessObjectProService) {
        this.businessObjectProService = businessObjectProService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }


    /**
     * 判断当前业务对象下是否有业务对象属性数据,有就全部删除
     *
     * @param rowId   业务对象rowId
     * @param request request请求
     * @param locale  国际化参数
     * @return serviceResult
     */
    @RequestMapping("/delete")
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> list = businessObjectProService
                .select(new FieldCondition("objRowId", Operator.EQUAL, rowId));
        if (UtilsTool.isValid(list)) {
            List<String> rowIds = list.stream().map((row) -> {
                return (String) row.get("rowId");
            }).collect(Collectors.toList());
            businessObjectProService.delete(new FieldCondition("rowId", Operator.IN, rowIds));
        }
        return super.delete(rowId, request, locale);
    }
}
