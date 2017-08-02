package com.bcx.plat.core.database.action.mapper;

import com.bcx.plat.core.database.action.DeleteAction;
import com.bcx.plat.core.database.action.InsertAction;
import com.bcx.plat.core.database.action.QueryAction;
import com.bcx.plat.core.database.action.UpdateAction;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Temp suit mapper.
 */
@Mapper
public interface TempSuitMapper {

  /**
   * 通用查询方法.
   *
   * @param queryActionLite 查询参数
   * @return 查询结果
   */
  List<Map<String, Object>> select(QueryAction queryActionLite);

  int insert(InsertAction insertAction);

  int delete(DeleteAction deleteAction);

  int update(UpdateAction deleteAction);
}

