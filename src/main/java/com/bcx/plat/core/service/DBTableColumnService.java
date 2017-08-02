package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.DBTableColumn;

import com.bcx.plat.core.utils.ServiceResult;
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
    ServiceResult<DBTableColumn> select(Map<String, Object> map);

    /**
     * 查询数据库表字段信息
     *
     * @param bean 数据表bean
     * @return 返回是否成功
     */
    ServiceResult<DBTableColumn> insert(DBTableColumn bean);

    /**
     * 更新数据库字段信息
     *
     * @param bean 数据表bean
     * @return 返回是否成功
     */
    ServiceResult<DBTableColumn> update(DBTableColumn bean);

    /**
     * 删除数据库字段信息
     *
     * @param bean 数据表bean
     * @return 返回是否成功
     */
    ServiceResult<DBTableColumn> delete(DBTableColumn bean);
}