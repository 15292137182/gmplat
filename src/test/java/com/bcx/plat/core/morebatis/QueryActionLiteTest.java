package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.mapper.PostgreSuitMapper;
import com.bcx.plat.core.morebatis.substance.Field;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.Table;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryActionLiteTest extends BaseTest {

  final Field fieldId = new Field("id");
  final Field fieldDemo1 = new Field("test_first");
  final Field fieldDemo2 = new Field("test_second");

  @Autowired
  private MoreBatis moreBatis;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  @Test
  public void smogTest() {
    QueryAction queryActionLite = moreBatis.select().select(fieldId, fieldDemo1, fieldDemo2)
        .from(new Table("test_only.test_table1"))
        .where(new FieldCondition(fieldId, Operator.IN, Arrays.asList(1, 2, 3)));
    final List<Map<String, Object>> select = queryActionLite.execute();
    Assert.assertTrue(select.size() > 0);
  }

  @Test
  public void andConditionTest() {
    QueryAction queryActionLite = moreBatis.select().select(fieldId, fieldDemo1, fieldDemo2)
        .from(new Table("test_only.test_table1"))
        .where(new And(new FieldCondition(fieldId, Operator.IN, Arrays.asList(1, 2, 3)),
            new FieldCondition(fieldId, Operator.EQUAL, 1)));
    Assert.assertTrue(queryActionLite.execute().size() == 1);
  }

  @Test
  public void selectAll() {
    QueryAction queryActionLite = moreBatis.select().selectAll()
        .from(new Table("test_only.test_table1"))
        .where(new FieldCondition(fieldId, Operator.IN, Arrays.asList(1, 2, 3)));
    final List<Map<String, Object>> result = queryActionLite.execute();
    Assert.assertTrue(result.get(0).entrySet().size() > 0);
  }
}