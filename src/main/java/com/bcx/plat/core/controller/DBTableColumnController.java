package com.bcx.plat.core.controller;

import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * Create By HCL at 2017/8/1
 */
@RestController
@RequestMapping("/plat/dbTableColumn")
public class DBTableColumnController {

    @Autowired
    private DBTableColumnService dbTableColumnService;

    /**
     * 数据库字段信息查询接口
     *
     * @return 返回查询信息
     */
    @RequestMapping("/select")
    public MappingJacksonValue select(String str, HttpServletRequest request) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("strArr", collectToSet(str));
        List<DBTableColumn> result = dbTableColumnService.select(cond);
        MappingJacksonValue value = new MappingJacksonValue(new ServiceResult("数据查询成功", result));
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }
}