package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Create By HCL at 2017/8/1
 */
@RestController
@RequestMapping("/core/dbTableColumn")
public class DBTableColumnController extends BaseControllerTemplate<DBTableColumnService, DBTableColumn> {

    private final BusinessObjectProService businessObjectProService;

    @Autowired
    public DBTableColumnController(BusinessObjectProService businessObjectProService) {
        this.businessObjectProService = businessObjectProService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("columnEname", "columnCname");
    }


    /**
     * 通过表信息字段rowId查询表信息并分页显示
     *
     * @param args     按照空格查询
     * @param rowId    接受rowId
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryPageById")
    public Object queryPageById(String args,String rowId,@RequestParam(value = "pageNum", defaultValue=BaseConstants.PAGE_NUM) int pageNum,
                                    @RequestParam(value = "pageSize" ,defaultValue = BaseConstants.PAGE_SIZE) int pageSize, String order, HttpServletRequest request, Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (args ==null && args.isEmpty()){
            pageNum = 1;
        }
        PageResult<Map<String, Object>> result = getEntityService()
                .select(new And(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId),
                                UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(args))),
                        Arrays.asList(QueryAction.ALL_FIELD), orders, pageNum, pageSize);
        return super.result(request, new ServiceResult(result,BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS), locale);
    }

    /**
     * 通用查询方法
     *
     * @param args    按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryTabById")
    public Object singleInputSelect(String args, String rowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = getEntityService().select(new And(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId),
                UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(args))));
        if (result.size() == 0) {
            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return super.result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
    }


    /**
     * 通用删除方法
     *
     * @param rowId   按照rowId查询
     * @param request request请求
     * @param locale  国际化参数
     * @return serviceResult
     */
    @RequestMapping("/delete")
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> busiPro = businessObjectProService.select(new FieldCondition("relateTableColumn", Operator.EQUAL, rowId));
        if (busiPro.size() == 0) {
            return super.delete(rowId, request, locale);
        }
        return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE), locale);
    }

}