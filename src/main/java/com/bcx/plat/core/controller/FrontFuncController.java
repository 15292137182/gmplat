package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.morebatis.substance.Field;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.FrontFuncService;

import java.util.*;
import java.util.stream.Collectors;

import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 前端功能模块 Created by Went on 2017/8/2.
 */
@RequestMapping("/core/fronc")
@RestController
public class FrontFuncController extends BaseControllerTemplate<FrontFuncService, FrontFunc> {
    @Autowired
    private FrontFuncProService frontFuncProService;
    @Autowired
    private BusinessObjectService businessObjectService;

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public void setFrontFuncProService(FrontFuncProService frontFuncProService) {
        this.frontFuncProService = frontFuncProService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("funcCode", "funcName", "funcType");
    }

    /**
     * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     */
    @RequestMapping("/query")
    public Object singleInputSelect(String str,String rowId ,HttpServletRequest request, Locale locale) {
        final FrontFuncService entityService = getEntityService();
        List<Map<String, Object>> result = entityService
            .select(new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
                entityService.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
        result=queryResultProcess(result);
        return result(request,new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result),locale);
    }

    /**
     * TODO 这个方法后面会有大用处
     * 暂时先放这里 以后再重构
     * @param result
     * @return
     */
    @Override
    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) -> {
            return (String) row.get("relateBusiObj");
        }).collect(Collectors.toList());
        List<Map<String, Object>> results = businessObjectService
            .select(new FieldCondition("rowId", Operator.IN, rowIds)
                , new Field("row_id", "rowId")
                , new Field("object_name", "objectName"));
        HashMap<String,Object> map=new HashMap<>();
        for (Map<String, Object> row : results) {
            map.put((String) row.get("rowId"),row.get("objectName"));
        }
        for (Map<String, Object> row : result) {
            row.put("objectName",map.get(row.get("relateBusiObj")));
        }
        return result;
    }

    /**
     * 删除
     *
     * @param rowId
     * @param request
     * @param locale
     */
    @RequestMapping("/delete")
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> list = frontFuncProService
            .select(new FieldCondition("funcRowId", Operator.EQUAL, rowId));
        if (UtilsTool.isValid(list)) {
            List<String> rowIds = list.stream().map((row) -> {
                return (String) row.get("rowId");
            }).collect(Collectors.toList());
            frontFuncProService.delete(new FieldCondition("rowId", Operator.IN, rowIds));
        }
        return super.delete(rowId, request, locale);
    }
}
