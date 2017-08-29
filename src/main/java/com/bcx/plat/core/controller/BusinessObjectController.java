package com.bcx.plat.core.controller;


import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.*;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final FrontFuncProService frontFuncProService;
    private final BusinessObjectService businessObjectService;

    @Autowired
    public BusinessObjectController(BusinessObjectProService businessObjectProService,
                                    MaintDBTablesService maintDBTablesService,
                                    FrontFuncService frontFuncService,
                                    FrontFuncProService frontFuncProService,
                                    BusinessObjectService businessObjectService) {
        this.businessObjectProService = businessObjectProService;
        this.maintDBTablesService = maintDBTablesService;
        this.frontFuncService = frontFuncService;
        this.businessObjectService = businessObjectService;
        this.frontFuncProService = frontFuncProService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }


    /**
     * 根据业务对象rowId查询当前数据
     *
     * @param rowId   唯一标识
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryById")
    @Override
    public Object queryById(String rowId, HttpServletRequest request, Locale locale) {
        return super.result(request, ServiceResult.Msg(businessObjectService.queryById(rowId)), locale);
    }

    /**
     * 查询业务对象全部数据并分页显示
     *
     * @param search     按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryPage")
    @Override
    public Object singleInputSelect(String search,
                            @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                            @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                            String order,HttpServletRequest request,Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (search == null && search.isEmpty()) {
            pageNum = 1;
        }
        return super.result(request, ServiceResult.Msg(businessObjectService.queryPage(search, pageNum, pageSize, orders)), locale);
    }


    /**
     * 根据业务对象rowId查找当前对象下的所有属性并分页显示
     *
     * @param search     按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryProPage")
    public Object queryProPage(String rowId,  String search,
                               @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                               String order, HttpServletRequest request,  Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        return super.result(request,ServiceResult.Msg(businessObjectService.queryProPage(search,rowId,pageNum,pageSize,orders)),locale);
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
        if (rowId==null) {
            return ServiceResult.ErrorMsg(PlatResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL));
        }
        return super.result(
                request,ServiceResult.Msg(
                        new PlatResult(
                                BaseConstants.STATUS_SUCCESS,Message.OPERATOR_SUCCESS,
                                businessObjectService.changeOperat(rowId))),locale);
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
        for (Map<String, Object> busin : businObj) {
            String rowId1 = (String) busin.get("rowId");
            List<Map<String, Object>> funcRowId = frontFuncProService.select(new FieldCondition("funcRowId", Operator.EQUAL, rowId1));
            if (funcRowId.size() != 0) {
                return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE)), locale);
            }
        }
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
        return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE)), locale);
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
                                , new Field("table_schema", "tableSchema")), null);
        HashMap<String, Object> map = new HashMap<>();
        for (Map<String, Object> row : results) {
            map.put((String) row.get("rowId"), row.get("tableCname"));
        }
        for (Map<String, Object> row : result) {
            row.put("associatTable", map.get(row.get("relateTableRowId")));
        }
        return result;
    }
}
