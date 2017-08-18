package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConditionBuilderContext {
  private Map<String, Column> columns;
  private TableSource tableSource;
  private Class<? extends BaseEntity> clz;
  private Condition condition;
  private static final MoreBatis moreBatis=SpringContextHolder.getBean("moreBatis");

  public ConditionBuilderContext(Class<? extends BaseEntity> clz) {
    this.clz = clz;
    tableSource = moreBatis.getTable(clz);
    columns = new HashMap<>();
    for (Column column : (Collection<Column>)moreBatis.getColumns(clz)) {
      columns.put(column.getAlies(),column);
    }
  }

  public Column getColumns(String alies) {
    return columns.get(alies);
  }

  public Collection<Column> getColumns() {
    return columns.values();
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public Class<? extends BaseEntity> getClz() {
    return clz;
  }

  public Condition getCondition() {
    return condition;
  }


}