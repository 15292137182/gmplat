package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.DBTableColumn;

import java.util.List;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public interface DBTableColumnService {

    /**
     * 查询数据方法
     *
     * @param map 查询条件
     * @return 返回类型
     */
    List<DBTableColumn> select(Map<String, Object> map);
}