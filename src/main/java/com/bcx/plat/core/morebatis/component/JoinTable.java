package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.util.MoreBatisUtil;
import java.util.LinkedList;

public class JoinTable implements TableSource<JoinTable> {
  TableSource tableFirst,tableSecond;
  JoinType joinType;
  Condition condition;

  public JoinTable(TableSource tableFirst,JoinType joinType,TableSource tableSecond) {
    this.tableFirst = tableFirst;
    this.tableSecond = tableSecond;
    this.joinType = joinType;
  }

  public TableSource getTableFirst() {
    return tableFirst;
  }

  public void setTableFirst(TableSource tableFirst) {
    this.tableFirst = tableFirst;
  }

  public TableSource getTableSecond() {
    return tableSecond;
  }

  public void setTableSecond(TableSource tableSecond) {
    this.tableSecond = tableSecond;
  }

  public JoinType getJoinType() {
    return joinType;
  }

  public void setJoinType(JoinType joinType) {
    this.joinType = joinType;
  }

  public Condition getCondition() {
    return condition;
  }

  public void setCondition(Condition condition) {
    this.condition = condition;
  }

  public JoinTable on(Condition condition){
    setCondition(condition);
    return this;
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return translator.translate(this);
  }
}