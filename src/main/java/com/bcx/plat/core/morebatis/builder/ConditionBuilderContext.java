package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConditionBuilderContext {
  private TableSource tableSource;
  private Class<? extends BaseEntity> clz;
  private static final MoreBatis moreBatis=SpringContextHolder.getBean("moreBatis");

  public ConditionBuilderContext(Class<? extends BaseEntity> clz) {
    this.clz = clz;
    tableSource = moreBatis.getTable(clz);
//    columns = new HashMap<>();
//    for (AliasedColumn aliasedColumn : (Collection<AliasedColumn>)moreBatis.getColumns(clz)) {
//      columns.put(aliasedColumn.getAlies(), aliasedColumn);
//    }
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public Class<? extends BaseEntity> getClz() {
    return clz;
  }

}