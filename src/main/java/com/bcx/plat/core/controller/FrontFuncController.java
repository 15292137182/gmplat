package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.FrontFuncService;
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
 * 前端功能模块 Created by Went on 2017/8/2.
 */
@RequestMapping("/core/fronc")
@RestController
public class FrontFuncController extends BaseControllerTemplate<FrontFuncService, FrontFunc> {
    private final FrontFuncProService frontFuncProService;
    private final BusinessObjectService businessObjectService;

    @Autowired
    public FrontFuncController(FrontFuncProService frontFuncProService, BusinessObjectService businessObjectService) {
        this.frontFuncProService = frontFuncProService;
        this.businessObjectService = businessObjectService;
    }

    /**
     * 需要模糊的搜索字段
     *
     * @return 表字段
     */
    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("funcCode", "funcName");
    }

    /**
     * 根据功能块rowId查询功能块属性
     *
     * @param str     空格查询
     * @param rowId   功能块rowId
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryPro")
    public Object singleQuery(String str, String rowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = frontFuncProService
                .select(new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
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
    //"[{\"str\":\"23\", \"num\":1},{\"str\":\"12\", \"num\":0},{\"str\":\"as\", \"num\":1}]"
    @RequestMapping("/queryProPage")
    public Object queryProPage(String rowId, String args,@RequestParam(value = "pageNum" ,defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = BaseConstants.PAGE_SIZE) int pageSize,String orde,
                                    HttpServletRequest request, Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(orde);
        PageResult<Map<String, Object>> result =
                frontFuncProService.select(
                        new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
                                UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(args)))
                        , Arrays.asList(QueryAction.ALL_FIELD), orders, pageNum, pageSize);
        result = queryResultProcess(result);
        return result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
    }

    /**
     * TODO 这个方法后面会有用处
     * 暂时先放这里 以后再重构
     *
     * @param result 接受serviceResult封装的参数
     * @return list
     */
    @Override
    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) -> {
            return (String) row.get("relateBusiObj");
        }).collect(Collectors.toList());
        List<Map<String, Object>> results = businessObjectService
                .select(new FieldCondition("rowId", Operator.IN, rowIds)
                        ,Arrays.asList( new Field("row_id", "rowId")
                        , new Field("object_name", "objectName")),null);
        HashMap<String, Object> map = new HashMap<>();
        for (Map<String, Object> row : results) {
            map.put((String) row.get("rowId"), row.get("objectName"));
        }
        for (Map<String, Object> row : result) {
            row.put("objectName", map.get(row.get("relateBusiObj")));
        }
        return result;
    }

    /**
     * 判断当前前端功能块下是否有功能块对应的属性数据,有就全部删除
     *
     * @param rowId   功能块rowId
     * @param request request请求
     * @param locale  国际化参数
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
