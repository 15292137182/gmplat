package com.bcx.plat.core.controller;

import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.service.DBTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create By HCL at 2017/8/1
 */
@RestController("plat/dbTableColumn")
public class DBTableColumnController {

    @Autowired
    private DBTableColumnService dbTableColumnService;

    /**
     * 数据库字段信息查询接口
     *
     * @return 返回查询信息
     */
    @RequestMapping("/select")
    @ResponseBody
    public List<DBTableColumn> select() {
        return null;
    }
}