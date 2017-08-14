package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
public class JoinTableTest extends BaseTest{
  @Autowired
  private MoreBatis moreBatis;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  @Test
  public void test(){
    QueryAction joinTableTest = moreBatis.select().selectAll()
        .from(new JoinTable(TableInfo.T_BUSINESS_OBJECT, JoinType.INNER_JOIN,
            TableInfo.T_BUSINESS_OBJECT_PRO)
            .on(new FieldCondition(new Field("t_business_object.row_id"), Operator.EQUAL,
                new Field("t_business_object_pro.obj_row_id"))));
    joinTableTest.execute();
  }
}
