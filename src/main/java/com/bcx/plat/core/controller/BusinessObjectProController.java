package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.PlatResult;
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

    @Autowired
    private FrontFuncProService frontFuncProService;
    @Autowired
    private BusinessObjectProService businessObjectProService;


    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("propertyCode", "propertyName");
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
        if (!UtilsTool.isValid(rowId)) {
            return ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL));
        }
        return super.result(request,ServiceResult.Msg(businessObjectProService.queryById(rowId)),locale);
    }

    /**
     * 根据业务对象rowId查询当前业务对象下的所有属性
     *
     * @param search      按照条件搜索
     * @param objRowId 根据业务对象rowId查找业务对象下所有属性
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryBusinPro")
    public Object querySlave(String search, String objRowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = getEntityService()
                .select(new And(new FieldCondition("objRowId", Operator.EQUAL, objRowId),
                        UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search))));
        if (result.size() == 0) {
            return result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
        return result(request, ServiceResult.Msg(new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS,result)), locale);
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
        return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE)), locale);
    }
}
