package com.bcx.plat.core.morebatis.command;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.ConditionTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.Map;

public class UpdateAction {

  private TableSource tableSource;
  private Map<String, Object> values;
  private Condition where;
  private MoreBatis app;
  private ConditionTranslator translator;

  public UpdateAction(MoreBatis app,ConditionTranslator translator) {
    this.app = app;
    this.translator = translator;
  }

  public ConditionTranslator getTranslator() {
    return translator;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public void setTableSource(TableSource tableSource) {
    this.tableSource = tableSource;
  }

  public Map<String, Object> getValues() {
    return values;
  }

  public void setValues(Map<String, Object> values) {
    this.values = values;
  }

  public Condition getWhere() {
    return where;
  }

  public void setWhere(Condition where) {
    this.where = where;
  }

  public UpdateAction where(Condition condition) {
    setWhere(condition);
    return this;
  }

  public UpdateAction from(TableSource tableSource) {
    setTableSource(tableSource);
    return this;
  }

  public UpdateAction set(Map<String, Object> row) {
    setValues(row);
    return this;
  }

  public int execute(){
    return app.execute(this);
  }
}
