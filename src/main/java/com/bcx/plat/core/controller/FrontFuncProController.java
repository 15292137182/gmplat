package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.service.FrontFuncService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;


/**
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping("/core/fronFuncPro")
public class FrontFuncProController extends BaseControllerTemplate<FrontFuncProService,FrontFuncPro>{

    @Autowired
    private FrontFuncProService frontFuncProService;
    @Autowired
    private BusinessObjectProService businessObjectProService;


    public void setFrontFuncProService(FrontFuncProService frontFuncProService) {
        this.frontFuncProService = frontFuncProService;
    }

    public void setBusinessObjectProService(BusinessObjectProService businessObjectProService) {
        this.businessObjectProService = businessObjectProService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return null;
    }

    /**
     * 查询业务对象
     *
     * @param str
     * @param request
     * @param locale
     */
    @RequestMapping("/query")
    @Override
    public Object singleInputSelect(String str, HttpServletRequest request, Locale locale) {
        String rowId = request.getParameter("rowId");
        String tables = request.getParameter("tables");
        String proRowId = request.getParameter("proRowId");
        if (rowId == null && tables == null && proRowId == null) {
            return new ServiceResult<>().Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        try {
         if(proRowId.length()!=0){
            Map<String,Object> map = new HashMap<>();
            map.put("rowId",proRowId);
            ServiceResult<List<Map<String, Object>>> result = frontFuncProService.select(map);
            return super.result(request,result,locale);
        }
         ServiceResult<List<Map<String, Object>>> result =
                frontFuncProService.singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));
        return super.result(request,result, locale);
        }catch (Exception e){
            e.printStackTrace();
            ServiceResult result = new ServiceResult<>().Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
            return super.result(request,result,locale);
        }
    }
}
//
//
//if (tables.length()!=0){
//        Map<String,Object> map = new HashMap<>();
//        map.put("funcRowId",tables);
//        ServiceResult<List<Map<String, Object>>> result = frontFuncProService.select(map);
//        return super.result(request,result,locale);
//        }
