package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 业务对象属性Controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping("/core/businObjPro")
@RestController
public class BusinessObjectProController extends
        BaseControllerTemplate<BusinessObjectProService, BusinessObjectPro> {

    final private FrontFuncProService frontFuncProService;
    final private DBTableColumnService dbTableColumnService;

    @Autowired
    public BusinessObjectProController( FrontFuncProService frontFuncProService,DBTableColumnService dbTableColumnService) {
        this.frontFuncProService = frontFuncProService;
        this.dbTableColumnService = dbTableColumnService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("propertyCode", "propertyName");
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
     * 根据业务对象属性rowId查询当前数据
     *
     * @param rowId     唯一标识
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryById")
    @Override
    public Object queryById(String rowId,HttpServletRequest request,Locale locale) {

        List<Map<String, Object>> result = getEntityService()
                .select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        String relateTableColumn = (String)result.get(0).get("relateTableColumn");

        List<Map<String, Object>> rowId1 =
                dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableColumn));
        if (rowId1.size()==0) {
            return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
        }
        for (Map<String ,Object> row :result){
            row.put("columnCname",rowId1.get(0).get("columnCname"));
        }
        return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
    }



    /**
     * 根据业务对象rowId查询当前业务对象下的所有属性
     *
     * @param str      按照条件搜索
     * @param objRowId 根据业务对象rowId查找业务对象下所有属性
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/querySlave")
    public Object querySlave(String str, String objRowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = getEntityService()
                .select(new And(new FieldCondition("objRowId", Operator.EQUAL, objRowId),
                        UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
        if (result.size() == 0) {
            return result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return result(request, new ServiceResult<>(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
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
        List<Map<String, Object>> busiPro = frontFuncProService.select(new FieldCondition("relateBusiPro", Operator.EQUAL, rowId));
        if (busiPro.size() == 0) {
            return super.delete(rowId, request, locale);
        }
        return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE), locale);
    }
}
