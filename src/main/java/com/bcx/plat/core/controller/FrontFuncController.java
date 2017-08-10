package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.FrontFuncService;

import java.util.*;
import java.util.stream.Collectors;

import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 前端功能模块 Created by Went on 2017/8/2.
 */
@RequestMapping("/core/fronc")
@RestController
public class FrontFuncController extends BaseControllerTemplate<FrontFuncService, FrontFunc> {

    @Autowired
    private FrontFuncService frontFuncService;
    @Autowired
    private FrontFuncProService frontFuncProService;
    @Autowired
    private BusinessObjectService businessObjectService;
    @Autowired
    private BusinessObjectProService businessObjectProService;

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public void setFrontFuncProService(FrontFuncProService frontFuncProService) {
        this.frontFuncProService = frontFuncProService;
    }

    public void setBusinessObjectProService(BusinessObjectProService businessObjectProService) {
        this.businessObjectProService = businessObjectProService;
    }

    public void setFrontFuncService(FrontFuncService frontFuncService) {
        this.frontFuncService = frontFuncService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("funcCode", "funcName", "funcType");
    }

    /**
     * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     */
    @RequestMapping("/query")
    public Object singleInputSelect(String str, HttpServletRequest request, Locale locale) {
        String rowId = request.getParameter("rowId");
        String rowIds = request.getParameter("rowIds");
        if (rowId == null && rowIds ==null) {
            ServiceResult serviceResult = new ServiceResult().Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
            return super.result(request, serviceResult, locale);
        }
        Map<String,Object> map1 = new HashMap<>();
        if (rowIds.length()==0){
            ServiceResult<List<Map<String, Object>>> result = frontFuncService.select(map1);

            List<Map<String, Object>> data = result.getData();
            Map<String, Object>map = new HashMap<>();
            for (int i =0;i<data.size();i++) {
                result.getData().get(i).get("relateBusiObj");
                Object relateBusiPro = data.get(i).get("relateBusiPro");
                map.put("rowId",relateBusiPro);
                ServiceResult<List<Map<String, Object>>> select = businessObjectService.select(map);
//                   String objectName =(String) select.getData().get(i).get();
//                   result.getData().get(i).put("tables",objectName);
            }
            return super.result(request,result,locale);
        }
        ServiceResult<List<Map<String, Object>>> result = frontFuncService
                .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));
        List<Map<String, Object>> data = result.getData();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            Object relateBusiObj = data.get(i).get("relateBusiObj");
            map.put("rowId", relateBusiObj);
            ServiceResult<List<Map<String, Object>>> select = businessObjectService.select(map);
            List<Map<String, Object>> data1 = select.getData();
            for (int j = 0; j < data1.size(); j++) {
                String objectName = (String) data1.get(i).get("objectName");
                result.getData().get(i).put("tables", objectName);
            }
            return super.result(request, result, locale);
        }
        ServiceResult<List<Map<String, Object>>> result1 =
                frontFuncService.singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));
        return super.result(request, result1, locale);
    }


    /**
     * 删除
     *
     * @param rowId
     * @param request
     * @param locale
     */
    @RequestMapping("/delete")
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        ServiceResult<List<Map<String, Object>>> list = frontFuncProService.singleInputSelect(Arrays.asList("funcRowId"), Arrays.asList(rowId));
        List<Map<String, Object>> data = list.getData();
        if (data != null) {
            List<String> rowIds = data.stream().map((row) -> {
                return (String) row.get("rowId");
            }).collect(Collectors.toList());
            Map<String, Object> argsSub = new HashMap<>();
            argsSub.put("rowId", rowIds);
            frontFuncProService.delete(argsSub);
        }
        Map<String, Object> args = new HashMap<>();
        args.put("rowId", rowId);
        ServiceResult<Object> result = frontFuncService.delete(args);
        return super.result(request, result, locale);
    }
}
