package com.bcx.plat.core.morebatis.mapper;

import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import java.util.List;
import java.util.Map;

public interface SuitMapper {

  List<Map<String,Object>> plainSelect(List list);

  int plainDelete(List list);

  int plainUpdate(List list);

  int plainInsert(List list);

  /**
   * 通用查询方法.
   *
   * @param queryActionLite 查询参数
   * @return 查询结果
   */
  List<Map<String,Object>> select(QueryAction queryActionLite);


  int insert(InsertAction insertAction);

  int delete(DeleteAction deleteAction);

  int update(UpdateAction deleteAction);
}
