package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping("/core/businObjPro")
@RestController
public class BusinessObjectProController extends BaseControllerTemplate<BusinessObjectProService, BusinessObjectPro> {

    @Autowired
    private BusinessObjectProService businessObjectProService;

    @Override
    public void setEntityService(BusinessObjectProService businessObjectProService) {
        super.setEntityService(businessObjectProService);
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("propertyCode", "propertyName");
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
        entity.setPropertyCode("A00"+UtilsTool.lengthUUID(3).toUpperCase());
        businessObjectProService.insert(entity.toMap());
        return super.insert(entity, request, locale);
    }

    /**
     * 查询业务对象
     *
     * @param str
     * @param request
     * @param locale
     */
    @RequestMapping("/query")
    @Override
    public Object singleInputSelect(String str, HttpServletRequest request, Locale locale) {
       String rowId =  request.getParameter("rowId");
       String rowIds =  request.getParameter("rowIds");
        if (str.length() != 0 && rowId.length() != 0) {
            Map<String, Object> args = new HashMap<>();
            args.put("objRowId", request.getParameter("rowId"));
            ServiceResult<List<Map<String, Object>>> result = businessObjectProService.select(args);
            return super.result(request, new ServiceResult().Msg(BaseConstants.STATUS_FAIL, Message.OPERATOR_FAIL), locale);
        }  else  if (str.length() != 0) {
            ServiceResult<List<Map<String, Object>>> result = businessObjectProService.singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));
            return super.result(request, result, locale);
        } else if (request.getParameter("rowId").length() != 0) {
            Map<String, Object> args = new HashMap<>();
            args.put("objRowId", rowId);
            ServiceResult<List<Map<String, Object>>> result = businessObjectProService.select(args);
            return super.result(request, result, locale);
        } else if (rowIds.length() != 0) {
            Map<String, Object> args = new HashMap<>();
            args.put("rowId", rowIds);
            ServiceResult<List<Map<String, Object>>> result = businessObjectProService.select(args);
            return super.result(request, result, locale);
        } else {
            return super.result(request, new ServiceResult().Msg(BaseConstants.STATUS_FAIL, Message.OPERATOR_FAIL), locale);

        }

    }
}
