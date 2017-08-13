package com.bcx.plat.core.morebatis.component;

import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.TableSource;

public class Join {
  TableSource tableFirst,tableSecond;
  JoinType joinType;
  Condition condition;

  public Join(TableSource tableFirst,JoinType joinType,TableSource tableSecond) {
    this.tableFirst = tableFirst;
    this.tableSecond = tableSecond;
    this.joinType = joinType;
  }
}
