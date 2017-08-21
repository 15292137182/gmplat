package com.bcx.plat.core.controller;


import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.database.info.Fields;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.*;
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

    @Autowired
    public BusinessObjectController(BusinessObjectProService businessObjectProService,
                                    MaintDBTablesService maintDBTablesService,
                                    FrontFuncService frontFuncService,
                                    FrontFuncProService frontFuncProService) {
        this.businessObjectProService = businessObjectProService;
        this.maintDBTablesService = maintDBTablesService;
        this.frontFuncService = frontFuncService;
        this.frontFuncProService = frontFuncProService;
    }




    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }


    /**
     * 通用查询方法
     *
     * @param args     按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryPage")
    public Object singleInputSelect(String args,
                                    @RequestParam(value = "pageNum", defaultValue=BaseConstants.PAGE_NUM) int pageNum,
                                    @RequestParam(value = "pageSize" ,defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                    String order,
                                    HttpServletRequest request,
                                    Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (args !=null && !args.isEmpty()){
            pageNum = 1;
        }
        PageResult<Map<String, Object>> result = getEntityService()
                .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(args), pageNum, pageSize, Arrays.asList(QueryAction.ALL_FIELD),orders);
        if (result.getResult().size()==0) {
            return super.result(request,ServiceResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL),locale);
        }
        for (Map<String,Object> rest : result.getResult()){
            rest.put("testDemo",false);
        }
        return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
    }

    /**
     * 接受参数和消息进行封装
     *
     * @param content 接受的参数
     * @param msg     消息
     * @param <T>
     * @return
     */
    private <T> ServiceResult<T> commonServiceResult(T content, String msg) {
        return new ServiceResult<>(content, BaseConstants.STATUS_SUCCESS, msg);
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
    public Object queryProPage(String rowId,
                               String args,
                               @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                               String order,
                               HttpServletRequest request,
                               Locale locale) {
        LinkedList<Order> str = UtilsTool.dataSort(order);
        PageResult<Map<String, Object>> result = null;

        if (UtilsTool.isValid(args)) {
            result = businessObjectProService.select(
                    new ConditionBuilder(BusinessObjectPro.class).buildByAnd()
                            .equal("objRowId", rowId).or()
                            .addCondition(UtilsTool.createBlankQuery(Arrays.asList("propertyCode", "propertyName"),
                                    UtilsTool.collectToSet(args))).endOr().endAnd().buildDone()
                    , Arrays.asList(QueryAction.ALL_FIELD), str, pageNum, pageSize);
            if (result.getResult().size() == 0) {
                return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
            }
            for (Map<String, Object> rest : result.getResult()) {
                rest.put("testDemo", false);
            }
            return super.result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
        }
        result =
                businessObjectProService.select(
                        new ConditionBuilder(BusinessObjectPro.class).buildByAnd()
                                .equal("objRowId", rowId).or().endOr().endAnd().buildDone()
                        , Arrays.asList(QueryAction.ALL_FIELD), str, pageNum, pageSize);

        if (result.getResult().size() == 0) {
            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        for (Map<String, Object> rest : result.getResult()) {
            rest.put("testDemo", false);
        }
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
            //获取当前版本号
            String version = (String)mapList.get(0).get("version");
            //转换为double类型
            double dou = new Double(version).doubleValue();
            //++当前版本号
            String  str = ++dou+"";
            // TODO 设置版本号 先++
            businessObject.setVersion(str);
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
        for (Map<String, Object> busin : businObj) {
            String rowId1 = (String) busin.get("rowId");
            List<Map<String, Object>> funcRowId = frontFuncProService.select(new FieldCondition("funcRowId", Operator.EQUAL, rowId1));
            if (funcRowId.size() != 0) {
                return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE), locale);
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
//            map.put((String) row.get("rowId"), row.get("tableSchema") + "(" + row.get("tableCname") + ")");
            map.put((String) row.get("rowId"), row.get("tableCname"));
        }
        for (Map<String, Object> row : result) {
            row.put("associatTable", map.get(row.get("relateTableRowId")));
        }
        return result;
    }
}
