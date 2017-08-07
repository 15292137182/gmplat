package com.bcx.plat.core.morebatis.mapper;

import com.bcx.plat.core.morebatis.DeleteAction;
import com.bcx.plat.core.morebatis.InsertAction;
import com.bcx.plat.core.morebatis.QueryAction;
import com.bcx.plat.core.morebatis.UpdateAction;
import java.util.List;
import java.util.Map;

public interface SuitMapper {
  /**
   * 通用查询方法.
   *
   * @param queryActionLite 查询参数
   * @return 查询结果
   */
  List<Map<String,Object>> select(QueryAction queryActionLite);

  List<Map<String,Object>> selectByOr(QueryAction queryActionLite);

  int insert(InsertAction insertAction);

  int delete(DeleteAction deleteAction);

  int update(UpdateAction deleteAction);
}
