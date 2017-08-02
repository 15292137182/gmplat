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

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
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
     * @param str     空格条件
     * @param rowId   主键编号
     * @param request 请求
     * @return 返回查询信息
     */
    @RequestMapping("/select")
    public MappingJacksonValue select(String str, String rowId, HttpServletRequest request) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("strArr", collectToSet(str));
        cond.put("rowId", rowId);
        List<DBTableColumn> result = dbTableColumnService.select(cond);
        MappingJacksonValue value = new MappingJacksonValue(new ServiceResult("数据查询成功", result));
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 新建数据
     *
     * @param dbTableColumn javaBean
     * @return 返回
     */
    @RequestMapping("/insert")
    public MappingJacksonValue insert(DBTableColumn dbTableColumn, HttpServletRequest request) {
        int status = dbTableColumnService.insert(dbTableColumn);
        ServiceResult serviceResult;
        if (status == 1) {
            serviceResult = new ServiceResult(STATUS_SUCCESS, "数据新建成功");
        } else {
            serviceResult = new ServiceResult(STATUS_FAIL, "数据新建失败！");
        }
        MappingJacksonValue value = new MappingJacksonValue(serviceResult);
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 更新数据
     *
     * @param dbTableColumn javaBean
     * @return 返回
     */
    @RequestMapping("/update")
    public MappingJacksonValue update(DBTableColumn dbTableColumn, HttpServletRequest request) {
        int status = dbTableColumnService.update(dbTableColumn);
        ServiceResult serviceResult;
        if (status == 1) {
            serviceResult = new ServiceResult(STATUS_SUCCESS, "数据更新成功");
        } else {
            serviceResult = new ServiceResult(STATUS_FAIL, "数据更新失败！");
        }
        MappingJacksonValue value = new MappingJacksonValue(serviceResult);
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }

    /**
     * 删除数据
     *
     * @param dbTableColumn javaBean
     * @return 返回
     */
    @RequestMapping("/delete")
    public MappingJacksonValue delete(DBTableColumn dbTableColumn, HttpServletRequest request) {
        int status = dbTableColumnService.delete(dbTableColumn);
        ServiceResult serviceResult;
        if (status == 1) {
            serviceResult = new ServiceResult(STATUS_SUCCESS, "数据删除成功");
        } else {
            serviceResult = new ServiceResult(STATUS_FAIL, "数据删除失败！");
        }
        MappingJacksonValue value = new MappingJacksonValue(serviceResult);
        value.setJsonpFunction(isValid(request.getParameter("callback")) ? request.getParameter("callback") : "callback");
        return value;
    }
}