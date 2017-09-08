package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 前端功能模块 Created by Went on 2017/8/2.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/fronc")
@RestController
public class FrontFuncController extends BaseController/*<FrontFuncService>*/ {
    private final FrontFuncService frontFuncService;
    private final FrontFuncProService frontFuncProService;
    private final BusinessObjectService businessObjectService;

    @Autowired
    public FrontFuncController(FrontFuncService frontFuncService,FrontFuncProService frontFuncProService, BusinessObjectService businessObjectService) {
        this.frontFuncService = frontFuncService;
        this.frontFuncProService = frontFuncProService;
        this.businessObjectService = businessObjectService;
    }

    /**
     * 需要模糊的搜索字段
     *
     * @return 表字段
     */
    /*@Override*/
    protected List<String> blankSelectFields() {
        return Arrays.asList("funcCode", "funcName");
    }

    /**
     * queryResultProcessAction
     *
     * @param result 接受serviceResult封装的参数
     * @return list
     */
    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        List<String> rowIds = result.stream().map((row) ->
                (String) row.get("relateBusiObj")).collect(Collectors.toList());

        List<BusinessObject> results = businessObjectService
                .select(new FieldCondition("rowId", Operator.IN, rowIds));
        HashMap<String, Object> map = new HashMap<>();
        for (BusinessObject row : results) {
            map.put(row.getRowId(), row.getObjectName());
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
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            List<FrontFuncPro> funcRowId = frontFuncProService
                    .select(new FieldCondition("funcRowId", Operator.EQUAL, rowId));

            if (UtilsTool.isValid(funcRowId)) {
                List<String> rowIds = funcRowId.stream().map((row) -> (String) row.getRowId()).collect(Collectors.toList());
                List<FrontFuncPro> frontFuncPros = frontFuncProService.select(new FieldCondition("rowId", Operator.IN, rowIds));
            }
            return /*deleteByIds(request, locale, rowId)*/ null;
        }
        return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }



    /**
     * 根据前端功能块的代码查询出对应的数据
     * @param funcCode ["obj102017-08-31000001"]
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/queryFuncCode")
    public Object queryFuncCode(String funcCode, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(funcCode)) {
            List list = UtilsTool.jsonToObj(funcCode, List.class);
            return super.result(request, PlatResult.Msg(frontFuncService.queryFuncCode(list)), locale);
        }
        return super.result(request, PlatResult.Msg(ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
    }




    /**
     * 通用新增方法
     *
     * @param entity  接受一个实体参数
     * @param request request请求
     * @param locale  国际化参数
     * @return 返回操作信息
     */
    @RequestMapping("/add")
    public Object insert(Map entity, HttpServletRequest request, Locale locale) {
        return /*super.insert(new FrontFunc().fromMap(entity), request, locale)*/ null;
    }


    /**
     * 通过修改方法
     *
     * @param entity  接受一个实体参数
     * @param request request请求
     * @param locale  国际化参数
     * @return 返回操作信息
     */
    @RequestMapping("/modify")
    public Object update(Map entity, HttpServletRequest request, Locale locale) {
        return /*super.updateById(new FrontFunc().fromMap(entity), request, locale)*/ null;
    }

}
