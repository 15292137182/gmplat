package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.MaintTableInfo;
import com.bcx.plat.core.service.MaintTableService;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Went on 2017/7/31.
 */
@RestController
@RequestMapping("core/maintTable")
public class MaintTableController extends BaseController {

    @Autowired
    private MaintTableService maintTableService;

    @RequestMapping("/query")
    public Object select(String str, HttpServletRequest request, Locale locale) {
        ServiceResult<MaintTableInfo> result = maintTableService.selectMaint(str);
        return super.result(request, result, locale);

    }

    @RequestMapping("/queryBuId")
    public Object selectById(String rowId, HttpServletRequest request, Locale locale) {
        ServiceResult<MaintTableInfo> result = maintTableService.selectById(rowId);
        return super.result(request, result, locale);

    }


}
