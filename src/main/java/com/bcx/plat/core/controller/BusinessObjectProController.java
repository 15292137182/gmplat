package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.usagetracker.UsageTrackerClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping("/core/businObjPro")
@RestController
public class BusinessObjectProController extends
        BaseControllerTemplate<BusinessObjectProService, BusinessObjectPro> {

    @Autowired
    BusinessObjectProService businessObjectProService;
    @Autowired
    FrontFuncProService frontFuncProService;

    public void setFrontFuncProService(FrontFuncProService frontFuncProService) {
        this.frontFuncProService = frontFuncProService;
    }

    public void setBusinessObjectProService(BusinessObjectProService businessObjectProService) {
        this.businessObjectProService = businessObjectProService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("propertyCode", "propertyName");
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
        final BusinessObjectProService entityService = getEntityService();
        List<Map<String, Object>> result = entityService
                .select(new And(new FieldCondition("objRowId", Operator.EQUAL, objRowId),
                        entityService.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(str))));
        if (result.size() == 0) {
            return result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return result(request, new ServiceResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result), locale);
    }


    /**
     * 根据业务对象属性的rowId查找当前记录
     *
     * @param businProrowId 业务对象属性rowId
     * @param request       request请求
     * @param locale        国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryPro")
    public Object singleInputSelect(String businProrowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> mapList =
                businessObjectProService.select(
                        new And(
                                new FieldCondition("rowId", Operator.EQUAL, businProrowId)));
        if (mapList.size() == 0) {
            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return super.result(request, new ServiceResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, mapList), locale);
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
    @RequestMapping("/queryProPage")
    public Object singleInputSelect(String rowId, String args, int pageNum, int pageSize,
                                    HttpServletRequest request, Locale locale) {
        PageResult<Map<String, Object>> result =
                businessObjectProService.select(
                        new And(new FieldCondition("objRowId", Operator.EQUAL, rowId),
                                businessObjectProService.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(args)))
                        ,pageNum,pageSize);
        return super.result(request, new ServiceResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result), locale);
    }



    /**
     * 通用删除方法
     *
     * @param rowId   按照rowId查询
     * @param request request请求
     * @param locale  国际化参数
     * @return
     */
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> busiPro = frontFuncProService.select(new FieldCondition("relateBusiPro", Operator.EQUAL, rowId));
        if (busiPro.size() == 0) {
            return super.delete(rowId, request, locale);
        }
        return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE), locale);
    }
}
