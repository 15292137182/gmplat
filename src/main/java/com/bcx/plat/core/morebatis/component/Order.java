package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;

public class Order implements AliasedColumn{
  public static final int DESC=0,ASC=1;

  private AliasedColumn aliasedColumn;

  private String alias;

  private int order=DESC;

  public Order(AliasedColumn aliasedColumn, int order) {
    this.aliasedColumn = aliasedColumn;
    this.order = order;
  }

  public Order(String alias, int order) {
    this.alias = alias;
    this.order = order;
  }

  public AliasedColumn getAliasedColumn() {
    return aliasedColumn;
  }

  public void setAliasedColumn(AliasedColumn aliasedColumn) {
    this.aliasedColumn = aliasedColumn;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return aliasedColumn.getColumnSqlFragment(translator);
  }

  @Override
  public String getAlias() {
    return aliasedColumn.getAlias();
  }

  @Override
  public String getFieldSource() {
    return aliasedColumn.getFieldSource();
  }
}
