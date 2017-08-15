package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import java.util.Arrays;
import java.util.List;

public class Order implements Column{
  public static final int DESC=0,ASC=1;

  private Column column;

  private int order=DESC;

  public Order(Column column, int order) {
    this.column = column;
    this.order = order;
  }

  public Column getColumn() {
    return column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  @Override
  public String getColumnSqlFragment(SqlComponentTranslator translator) {
    return column.getColumnSqlFragment(translator);
  }

  @Override
  public String getAlies() {
    return column.getAlies();
  }

  @Override
  public String getFieldSource() {
    return column.getFieldSource();
  }
}
