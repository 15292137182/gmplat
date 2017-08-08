package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public interface DBTableColumnService {


  /**
   * 查询方法
   *
   * @param map   查询参数
   * @return 返回
   */
  ServiceResult select(Map map);



  /**
   * 查询方法
   *
   * @param rowId   查询参数
   * @return 返回
   */
  ServiceResult selectByTableId(String rowId);


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
   * @param cond 数据
   * @return 操作结果状态
   */
  ServiceResult batchDelete(Map cond);
}