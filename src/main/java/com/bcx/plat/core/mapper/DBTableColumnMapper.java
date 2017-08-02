package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.entity.DBTableColumn;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据库字段信息 Create By HCL at 2017/7/31
 */
@Mapper
public interface DBTableColumnMapper extends BaseMapper<DBTableColumn> {

  /**
   * 逻辑删除的方法
   *
   * @param bean 数据Bean
   * @return 返回操作结果标识
   */
  int logicDelete(DBTableColumn bean);
}