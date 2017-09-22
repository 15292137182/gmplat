package com.bcx.plat.core.morebatis.command;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;

public class DeleteAction {

  private TableSource tableSource;
  private Condition where;
  private MoreBatis app;
  private SqlComponentTranslator translator;

  public DeleteAction(MoreBatis app, SqlComponentTranslator translator) {
    this.app = app;
    this.translator = translator;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public SqlComponentTranslator getTranslator() {
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

  public int execute() {
    return app.execute(this);
  }
}
