package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 前端功能模块属性Controller层
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping("/core/fronFuncPro")
public class FrontFuncProController extends
        BaseControllerTemplate<FrontFuncProService, FrontFuncPro> {

    private final BusinessObjectProService businessObjectProService;

    @Autowired
    public FrontFuncProController(BusinessObjectProService businessObjectProService) {
        this.businessObjectProService = businessObjectProService;
    }

    /**
     * 模糊查询的字段
     *
     * @return 字段
     */
    @Override
    protected List<String> blankSelectFields() {
        return Collections.singletonList("rowId");
    }

    /**
     * 查询
     */
    @RequestMapping("/queryProRowId")
    public Object singleQuery(String queryProRowId, HttpServletRequest request, Locale locale) {
        final FrontFuncProService entityService = getEntityService();
        List<Map<String, Object>> result = entityService
                .select(new And(new FieldCondition("rowId", Operator.EQUAL, queryProRowId)));
        result = queryResultProcess(result);
        if (result.size() == 0) {
            return result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
    }


    /**
     * 查询
     */
    @RequestMapping("/queryPro")
    public Object singleQuery(String str, String fronByProRowId, HttpServletRequest request, Locale locale) {
        final FrontFuncProService entityService = getEntityService();
        List<Map<String, Object>> result = entityService
                .select(new And(new FieldCondition("funcRowId", Operator.EQUAL, fronByProRowId),
                        UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
        result = queryResultProcess(result);
        if (result.size() == 0) {
            return result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
    }


    /**
     * 根据功能块rowId查找当前对象下的所有属性并分页显示
     *
     * @param args     按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryProPage")
    public Object singleInputSelect(String rowId, String args, @RequestParam(value = "pageNum" ,defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                    HttpServletRequest request, Locale locale,String order) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        PageResult<Map<String, Object>> result =
                getEntityService().select(
                        new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
                                UtilsTool.createBlankQuery(Collections.singletonList("displayTitle"), UtilsTool.collectToSet(args)))
                        ,Arrays.asList(QueryAction.ALL_FIELD),orders, pageNum, pageSize);
        result = queryResultProcess(result);
        return result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
    }


    /**
     * TODO 这个方法后面会有大用处
     * 暂时先放这里 以后再重构
     *
     * @param result 接受ServiceResult
     * @return list
     */
    @Override
    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) -> {
            return (String) row.get("relateBusiPro");
        }).collect(Collectors.toList());
        List<Map<String, Object>> results = businessObjectProService
                .select(new FieldCondition("rowId", Operator.IN, rowIds)
                        , Arrays.asList(new Field("row_id", "rowId")
                        , new Field("property_name", "propertyName")),null);
        HashMap<String, Object> map = new HashMap<>();
        for (Map<String, Object> row : results) {
            map.put((String) row.get("rowId"), row.get("propertyName"));
        }
        for (Map<String, Object> row : result) {
            row.put("propertyName", map.get(row.get("relateBusiPro")));
        }
        return result;
    }
}
