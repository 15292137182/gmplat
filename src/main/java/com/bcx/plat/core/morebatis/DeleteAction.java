package com.bcx.plat.core.morebatis;

import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.ConditionTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.Arrays;
import java.util.List;

public class DeleteAction {

  private TableSource tableSource;
  private Condition where;
  private ConditionTranslator translator;

  public DeleteAction() {
    translator= SpringContextHolder.getBean("conditionTranslator");
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public ConditionTranslator getTranslator() {
    return translator;
  }

  public void setTableSource(TableSource tableSource) {
    this.tableSource = tableSource;
  }

  public Condition getWhere() {
    return where;
  }

  public void setWhere(Condition where) {
    this.where = where;
  }

  public DeleteAction where(Condition condition) {
    setWhere(condition);
    return this;
  }

  public DeleteAction from(TableSource tableSource) {
    setTableSource(tableSource);
    return this;
  }
}
