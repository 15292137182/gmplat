package com.bcx.plat.core.database.action;

import com.bcx.plat.core.database.action.phantom.TableSource;
import com.bcx.plat.core.database.action.substance.FieldCondition;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UpdateAction {

  private TableSource tableSource;
  private Map<String, Object> values;
  private List<FieldCondition> where;

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

  public List<FieldCondition> getWhere() {
    return where;
  }

  public void setWhere(List<FieldCondition> where) {
    this.where = where;
  }


  public UpdateAction where(FieldCondition... fieldCondition) {
    return where(Arrays.asList(fieldCondition));
  }

  public UpdateAction where(List<FieldCondition> fieldCondition) {
    setWhere(fieldCondition);
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
}
