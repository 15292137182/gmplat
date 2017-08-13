package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.substance.Field;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.MaintDBTablesService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.*;
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

    @Autowired
    MaintDBTablesService maintDBTablesService;

    public void setMaintDBTablesService(MaintDBTablesService maintDBTablesService) {
        this.maintDBTablesService = maintDBTablesService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }

    /**
     * 执行变更操作
     *
     * @param rowId   业务对象rowId
     * @param request request请求
     * @param locale  国际化参数
     * @return serviceResult
     */
    @RequestMapping("/changeOperat")
    public Object changeOperat(String rowId, HttpServletRequest request, Locale locale) {
        Map<String, Object> map = new HashMap<>();
        if (UtilsTool.isValid(rowId)) {
            map.put("rowId", rowId);
            map.put("changeOperat", BaseConstants.CHANGE_OPERAT_SUCCESS);
            getEntityService().update(map);
            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS), locale);
        }
        return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL), locale);
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


    /**
     * 暂时先放这里 以后再重构
     *
     * @param result
     * @return
     */
    @Override
    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) -> {
            return (String) row.get("relateTableRowId");
        }).collect(Collectors.toList());
        List<Map<String, Object>> results = maintDBTablesService
                .select(new FieldCondition("rowId", Operator.IN, rowIds)
                        , new Field("row_id", "rowId")
                        , new Field("table_cname", "tableCname")
                        , new Field("table_schema", "tableSchema"));
        HashMap<String, Object> map = new HashMap<>();
        for (Map<String, Object> row : results) {
            map.put((String) row.get("rowId"), row.get("tableSchema") + "(" + row.get("tableCname") + ")");
        }
        for (Map<String, Object> row : result) {
            row.put("associatTable", map.get(row.get("relateTableRowId")));
        }
        return result;
    }
}
