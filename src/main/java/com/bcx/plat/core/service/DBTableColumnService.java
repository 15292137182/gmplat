package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Collection;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public interface DBTableColumnService extends BaseService<DBTableColumn>{

  /**
   * 查询数据方法
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  ServiceResult select(Map map);

  /**
   * 新建 数据库字段信息
   *
   * @param bean 数据表bean
   * @return 操作结果状态
   */
  ServiceResult insert(DBTableColumn bean) ;

  /**
   * 更新 数据库字段信息
   *
   * @param bean 数据表bean
   * @return 操作结果状态
   */
  ServiceResult update(DBTableColumn bean);

  /**
   * 删除 数据库字段信息
   *
   * @param rowIds 数据表 bean
   * @return 操作结果状态
   */
  ServiceResult batchDelete(Collection<String> rowIds);
}