package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 业务对象属性Controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/businObjPro")
@RestController
public class BusinessObjectProController extends
        BaseController/*<BusinessObjectProService> */{

    private final FrontFuncProService frontFuncProService;
    private final BusinessObjectProService businessObjectProService;

    @Autowired
    public BusinessObjectProController(FrontFuncProService frontFuncProService, BusinessObjectProService businessObjectProService) {
        this.frontFuncProService = frontFuncProService;
        this.businessObjectProService = businessObjectProService;
    }


    /*@Override*/
    protected List<String> blankSelectFields() {
        return Arrays.asList("propertyCode", "propertyName");
    }


    /**
     * 根据业务对象属性rowId查询当前数据
     *
     * @param rowId     唯一标识
     * @param request  request请求
     * @param locale   国际化参数
     * @return PlatResult
     */
    @RequestMapping("/queryById")
    public Object queryById(String rowId,HttpServletRequest request,Locale locale) {
//        String attrSource = request.getParameter("attrSource");//属性来源
//        attrSource =attrSource.equals("")?BaseConstants.ATTRIBUTE_SOURCE_BASE:BaseConstants.ATTRIBUTE_SOURCE_MODULE;
        if (!UtilsTool.isValid(rowId)) {
            return PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }else{
            return super.result(request, PlatResult.Msg(businessObjectProService.queryById(rowId)), locale);
        }

    }

    /**
     * 供前端功能块属性使用
     * 根据业务对象rowId查询当前业务对象下的所有属性
     *
     * @param objRowId 根据业务对象rowId查找业务对象下所有属性
     * @param request  request请求
     * @param frontRowId  功能块rowId
     * @param locale   国际化参数
     * @return PlatResult
     */
    @RequestMapping("/queryBusinPro")
    public Object queryBusinPro(String objRowId, String frontRowId,HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(objRowId)) {
            ServerResult serverResult = businessObjectProService.queryBusinPro(objRowId, frontRowId);
            return result(request, PlatResult.Msg(serverResult), locale);
        }else{
            return result(request, PlatResult.Msg(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, null)), locale);
        }
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
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        List<FrontFuncPro> frontFuncPros = frontFuncProService.select(new FieldCondition("relateBusiPro", Operator.EQUAL, rowId));
        if (frontFuncPros.size() == 0) {
/*
            return super.deleteByIds(request,locale,rowId);
*/
return null;
        }else{
            return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE)), locale);
        }
    }
}
