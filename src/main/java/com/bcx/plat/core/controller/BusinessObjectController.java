package com.bcx.plat.core.controller;


import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.database.info.Fields;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.service.MaintDBTablesService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务对象controller层
 * Created by wth on 2017/8/8.
 */
@RestController
@RequestMapping("/core/businObj")
public class BusinessObjectController extends
        BaseControllerTemplate<BusinessObjectService, BusinessObject> {

    private final BusinessObjectProService businessObjectProService;
    private final MaintDBTablesService maintDBTablesService;
    private final FrontFuncService frontFuncService;

    @Autowired
    public BusinessObjectController(BusinessObjectProService businessObjectProService, MaintDBTablesService maintDBTablesService, FrontFuncService frontFuncService) {
        this.businessObjectProService = businessObjectProService;
        this.maintDBTablesService = maintDBTablesService;
        this.frontFuncService = frontFuncService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }


    /**
     * 根据业务对象rowId查找当前对象下的所有属性并分页显示
     *
     * @param args     按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryProPage")
    public Object queryProPage(String rowId, String args, int pageNum, int pageSize,String order,
                                    HttpServletRequest request, Locale locale) {
        LinkedList<Order> str = UtilsTool.dataSort(order);
        PageResult<Map<String, Object>> result =
                businessObjectProService.select(
                        new And(new FieldCondition(Fields.T_BUSINESS_OBJECT_PRO.OBJ_ROW_ID, Operator.EQUAL, rowId),
                                UtilsTool.createBlankQuery(Arrays.asList("propertyCode", "propertyName"), UtilsTool.collectToSet(args)))
                        , Arrays.asList(QueryAction.ALL_FIELD), str, pageNum, pageSize);
        return super.result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
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
        Map<String, Object> oldRowId = new HashMap<>();
        List<Map<String, Object>> list = getEntityService().select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        List<String> row = list.stream().map((rowIds) -> {
            return (String) rowIds.get("changeOperat");
        }).collect(Collectors.toList());
        if (UtilsTool.isValid(rowId)&&row.get(0).equals(BaseConstants.CHANGE_OPERAT_FAIL)) {
            List<Map<String, Object>> mapList =
                    getEntityService().select(new FieldCondition("rowId", Operator.EQUAL, rowId));
            Map<String, Object> objectMap = mapList.get(0);
            //将Map数据转换为json结构的数据
            String toJson = UtilsTool.objToJson(objectMap);
            //将json数据转换为实体类
            BusinessObject businessObject = UtilsTool.jsonToObj(toJson, BusinessObject.class);
            //复制一份数据出来
            businessObject.setChangeOperat(BaseConstants.CHANGE_OPERAT_FAIL);
            businessObject.buildCreateInfo();
            businessObject.setVersion("2.0");
            String objectCode = (String)mapList.get(0).get("objectCode");
            businessObject.setObjectCode(objectCode);
            getEntityService().insert(businessObject.toMap());
            logger.info("复制业务对象数据成功");
            oldRowId.put("rowId", rowId);
            oldRowId.put("status",BaseConstants.INVALID);
            oldRowId.put("changeOperat", BaseConstants.CHANGE_OPERAT_SUCCESS);
            logger.info("修改变更状态成功");
            getEntityService().update(oldRowId);
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
        Map<String, Object> map = new HashMap<>();
        map.put("relateBusiObj", rowId);
        List<Map<String, Object>> businObj = frontFuncService.select(new FieldCondition("relateBusiObj", Operator.EQUAL, rowId));
        if (businObj.size() == 0) {
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
        return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE), locale);
    }


    /**
     * 暂时先放这里 以后再重构
     *
     * @param result 返回值
     * @return 返回值
     */
    @Override
    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) -> {
            return (String) row.get("relateTableRowId");
        }).collect(Collectors.toList());
        List<Map<String, Object>> results = maintDBTablesService
                .select(new FieldCondition("rowId", Operator.IN, rowIds)
                        , Arrays.asList(new Field("row_id", "rowId")
                        , new Field("table_cname", "tableCname")
                        , new Field("table_schema", "tableSchema")),null);
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
