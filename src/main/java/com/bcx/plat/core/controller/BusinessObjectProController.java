package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.service.BusinessObjectProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping("/core/businObjPro")
@RestController
public class BusinessObjectProController extends BaseControllerTemplate<BusinessObjectProService,BusinessObjectPro>{

    @Autowired
    private BusinessObjectProService businessObjectProService;
    @Override
    public void setEntityService(BusinessObjectProService businessObjectProService) {
        super.setEntityService(businessObjectProService);
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("propertyCode","propertyName");
    }

    /**
     * 新增业务对象:对象代码，对象名称，关联表(单选)，版本(系统生成)
     *
     * @param entity
     * @param request
     * @param locale
     */
    @RequestMapping("/add")
    @Override
    public Object insert(BusinessObjectPro entity, HttpServletRequest request, Locale locale) {
        entity.setPropertyCode("C000001");
        businessObjectProService.insert(entity.toMap());
        return super.insert(entity, request, locale);
    }

}
