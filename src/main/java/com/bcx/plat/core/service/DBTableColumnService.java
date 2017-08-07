package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Collection;

/**
 * Create By HCL at 2017/8/1
 */
public interface DBTableColumnService extends BaseService<DBTableColumn>{

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
   * @param rowIds 数据表bean
   * @return 返回是否成功
   */
  ServiceResult<DBTableColumn> batchDelete(Collection<String> rowIds);
}